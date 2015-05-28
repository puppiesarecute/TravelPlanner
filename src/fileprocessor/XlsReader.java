package fileprocessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class XlsReader
{

//    private final static String FILEPATH = "DistancesInJyllandTest.xls";
    private final static String FILEPATH = "DistancesInJylland.xls";
//    private final static String FILEPATH = "ShortestPath.xls";
    private static POIFSFileSystem fileSystem;
    private static HSSFWorkbook workBook = null;
    private static HSSFSheet sheet = getWorkBook().getSheetAt(0);
    private static HSSFRow row;
    private static HSSFCell cell;

    /**
     * gets the cities from the first column in the excell file and adds them to a list.
     * @return List<String>
     */
    public static List<String> getCitiesFromFile()
    {
	List<String> citieslist = new ArrayList<String>();

	int rows; // No of rows
	rows = sheet.getPhysicalNumberOfRows();
	// System.out.println("Physical no of row: " + rows);

	int columns = 0; // No of columns

	for (int r = 0; r < rows; r++)
	{
	    row = sheet.getRow(r);

	    // if row contains data, get the cell at first colum
	    if (row != null)
	    {
		cell = row.getCell(columns);
		if (cell != null)
		{
		    String cities = row.getCell(columns).toString();
		    citieslist.add(cities);
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
	return citieslist;
    }

    /**
     * gets the distances from city to city and adds them to a 2d int array.
     * @return int[][]
     */
    public static int[][] getDistances()
    {

	// -1 because we don't want to count the first row of city names
	int rows = sheet.getPhysicalNumberOfRows() - 1;

	// -1 because we don't want to count the first column of city names
	int cols = sheet.getRow(2).getPhysicalNumberOfCells();

	int[][] array = new int[rows][cols];

	// Looping through sheet and populating array
	for (int r = 1; r <= rows; r++)
	{
	    row = sheet.getRow(r);
	    if (row != null)
	    {
		for (int c = 1; c <= cols; c++)
		{
		    cell = row.getCell(c);

		    if (cell != null && (int) cell.getNumericCellValue() != 0)
		    {
			int distanceInt = (int) cell.getNumericCellValue();
			array[r - 1][c - 1] = distanceInt;
		    }
		    else
		    {
			array[r - 1][c - 1] = Integer.MAX_VALUE;
		    }
//		    TEST: System.out.println("row: " + (r-1) + " && col: " + (c-1) + " value: " + array[r-1][c-1] );
		}
	    }
	}
	try
	{
	    workBook.close();
	}

	catch (IOException e)
	{
	    e.printStackTrace();
	}

	return array;
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
    
    public static void saveFile(List<String> list)
    {
	try
	{
	    PrintWriter writer = new PrintWriter("Cities.txt", "UTF-16");
	    for (String cities : list)
	    {
		writer.println(cities);
	    }
	    writer.close();
	}
	catch (FileNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (UnsupportedEncodingException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}