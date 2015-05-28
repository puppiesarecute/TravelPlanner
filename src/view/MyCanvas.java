package view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import fileprocessor.CityReader;

public class MyCanvas extends Canvas
{
    BufferedImage image;
    Map<String, CityCoordinate> coordinates = CityReader.readCitiesCoordinate();
    
    public MyCanvas()
    {
	try
	{
	    image = ImageIO.read(new FileInputStream("DenmarkMap.png"));
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Override
    public void paint(Graphics g)
    {
	super.paint(g);
	g.drawImage(image, 0, 0, this);
	g.setColor(Color.RED);

	Graphics2D g2d = (Graphics2D) g.create();
	g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	g2d.setColor(Color.RED);
	
	for (String city : coordinates.keySet())
	{
	    g2d.drawOval(coordinates.get(city).getX()-2, coordinates.get(city).getY()-2, 4, 4);
	}	
    }
    
    public void drawConnectorLine(String originCity, String targetCity, Color color)
    {
	
	Graphics g = this.getGraphics();
	Graphics2D g2d = (Graphics2D) g.create();
	g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	g2d.setColor(color);
	g2d.drawLine(this.coordinates.get(originCity).getX(),this.coordinates.get(originCity).getY(), this.coordinates.get(targetCity).getX(), this.coordinates.get(targetCity).getY());
    }
}

