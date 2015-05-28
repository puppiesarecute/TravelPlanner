package graphimplementation;

import java.util.ArrayList;
import java.util.List;
import dijkstra.*;

import fileprocessor.XlsReader;

public class AdjacentListGraph
{
    public List<Vertex> nodes;
    // TODO: change data type of nodes to Map<String, Vertex> to avoid hard-coding number 11 for CPH city
    int[][] adjMatrix;

    public AdjacentListGraph()
    {
	this.nodes = new ArrayList<Vertex>();
	for (String string : XlsReader.getCitiesFromFile())
	{
	    if (!string.isEmpty())
	    {
		this.nodes.add(new Vertex(string));
	    }
	}
	this.adjMatrix = XlsReader.getDistances();
	for (Vertex node : nodes)
	{
	    // TEST: System.out.println("Node: " + node + "\n-----" );
	    addEdgeForVertex(node);
	}
    }

    private void addEdgeForVertex(Vertex v)
    {
	// find v.adjacencies (Edge[] for the node v)
	ArrayList<Edge> edges = new ArrayList<Edge>();
	// v.adjacencies = new Edge[nodes.size()];
	for (int i = 0; i < nodes.size(); i++)
	{
	    // System.out.println("Vertex: " + v.toString());
	    // v.adjacencies[i] = new Edge(nodes.get(i), adjMatrix[nodes.indexOf(v)][i]);
	    if (adjMatrix[nodes.indexOf(v)][i] < Integer.MAX_VALUE)
	    {
		edges.add(new Edge(nodes.get(i), adjMatrix[nodes.indexOf(v)][i]));
	    }
	    // TEST: System.out.println(v.adjacencies[i]);
	}

	v.adjacencies = new Edge[edges.size()];
	for (int i = 0; i < edges.size(); i++)
	{
	    v.adjacencies[i] = edges.get(i);
	}
    }

}
