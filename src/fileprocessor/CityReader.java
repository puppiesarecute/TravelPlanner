package fileprocessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import view.CityCoordinate;

public class CityReader
{

    private final static String FILEPATH = "CityCoordinatesFile.xls";
    private static POIFSFileSystem fileSystem;
    private static HSSFWorkbook workBook = null;
    private static HSSFSheet sheet = getWorkBook().getSheetAt(0);
    private static HSSFRow row;
    private static HSSFCell cell;

    public static Map<String, CityCoordinate> readCitiesCoordinate()
    {
	Map<String, CityCoordinate> coordinates = new HashMap<String, CityCoordinate>();
	
	int rows = sheet.getPhysicalNumberOfRows();

	// -1 because we don't want to count the first column of city names
	int cols = sheet.getRow(2).getPhysicalNumberOfCells() - 1;

//	CityCoordinate[] cities = new CityCoordinate[rows];
	for (int i = 0; i < rows; i++)
	{
	    row = sheet.getRow(i);

	    // if row contains data, get the cell at first colum
	    if (row != null)
	    {
		cell = row.getCell(0);
		if (cell != null)
		{
		    CityCoordinate city = new CityCoordinate(cell.toString());
		    city.setX((int)Double.parseDouble(row.getCell(1).toString()));
		    city.setY((int)Double.parseDouble(row.getCell(2).toString()));
		    coordinates.put(cell.toString(), city);
		}
	    }
	}
	try
	{
	    workBook.close();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return coordinates;
    }

    private static HSSFWorkbook getWorkBook()
    {
	try
	{
	    POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(FILEPATH));

	    workBook = new HSSFWorkbook(fileSystem);
	}
	catch (Exception ioe)
	{
	    ioe.printStackTrace();
	}

	return workBook;

    }

}
