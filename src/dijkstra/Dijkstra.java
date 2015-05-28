//package dk.kea.swc3.dijkstra.alex;
package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra
{   
    public static void computePaths(Vertex source)
    {
	source.minDistance = 0.0;
	PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();
	vertexQueue.add(source);

	while (!vertexQueue.isEmpty())
	{
	    Vertex headNode = vertexQueue.poll();

	    // Visit each edge exiting u
	    for (Edge e : headNode.adjacencies)
	    {
		Vertex targetNode = e.target;
		double weight = e.weight;
		double distanceThroughHeadNode = headNode.minDistance + weight;
		if (distanceThroughHeadNode < targetNode.minDistance)
		{
		    vertexQueue.remove(targetNode);
		    targetNode.minDistance = distanceThroughHeadNode;
		    targetNode.previous = headNode;
		    vertexQueue.add(targetNode);
		}
	    }
	}
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
	List<Vertex> path = new ArrayList<>();
	for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
	{
	    path.add(vertex);
	}
	Collections.reverse(path);
	return path;
    }

   
}