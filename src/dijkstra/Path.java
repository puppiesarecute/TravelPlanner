package dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path
{
    private double distance;
    private Vertex[] path;

    public Path(Vertex[] path)
    {
	this.path = path;
	this.distance = 0;
    }
    
    public double getDistance()
    {
	return distance;
    }
    
    public Vertex[] getPath()
    {
	return path;
    }
    
    public void setDistance(double distance)
    {
	this.distance = distance;
    }
    
    public void setPath(Vertex[] path)
    {
	this.path = path;
    }
    
    @Override
    public String toString()
    {
	String result = "";
	for (int i = 0; i < path.length; i++)
	{
	    result += path[i] + "---";
	}
	return result;
    }
}
