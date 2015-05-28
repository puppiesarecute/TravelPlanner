package dijkstra;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(String name)
    {
	this.name = name;
    }

    @Override
    public String toString()
    {
	return name;
    }

    @Override
    public int compareTo(Vertex other)
    {
	return Double.compare(minDistance, other.minDistance);
    }
    
    public void reset()
    {
	this.minDistance = Double.POSITIVE_INFINITY;
	this.previous = null;
    }
//    
//    public boolean equals(Vertex other)
//    {
//	return this.name.equals(other.name);
//    }
}