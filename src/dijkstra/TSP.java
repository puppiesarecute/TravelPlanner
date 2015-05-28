package dijkstra;

import graphimplementation.AdjacentListGraph;

import java.util.ArrayList;
import java.util.List;

public class TSP
{
    public static List<String> greedyFriendlyOutput(List<String> inputCities)
    {
	List<String> citiesNames = new ArrayList<String>();
	List<Vertex> resultVertex = new ArrayList<Vertex>();
	resultVertex = greedyAlgorithm(inputCities);
	for (Vertex vertex : resultVertex)
	{
	    citiesNames.add(vertex.name);
	}
	return citiesNames;
    }

    public static List<Vertex> greedyAlgorithm(List<String> inputCities)
    {
	AdjacentListGraph graph = new AdjacentListGraph();
	List<Vertex> inputNodes = new ArrayList<Vertex>();

	// convert cities names into Vertex of graph
	for (Vertex v : graph.nodes)
	{
	    for (String string : inputCities)
	    {
		if (v.name.equals(string))
		{
		    inputNodes.add(v);
		}
	    }
	}

	return greedyAlgorithm(graph.nodes.get(11), inputNodes, graph);
    }

    public static List<Vertex> greedyAlgorithm(Vertex origin, List<Vertex> inputCities, AdjacentListGraph graph)
    {
	List<Vertex> output = new ArrayList<Vertex>();
	double totalDist = 0;
	if (inputCities.size() == 1)
	{
	    output.add(origin);
	    output.add(inputCities.get(0));
	}
	else if (inputCities.size() > 1)
	{
	    // Vertex origin = graph.nodes.get(11); // get CPH city as origin
	    output.add(origin);

	    for (Vertex vertex : inputCities)
	    {
		vertex.reset();
	    }
	    Dijkstra.computePaths(origin);

	    Vertex nextCity = getClosestNode(inputCities);
	    // System.out.println("Next city is " + nextCity);
	    inputCities.remove(nextCity);
	    origin = nextCity;
	    output.addAll(greedyAlgorithm(origin, inputCities, graph));
	}

	return output;

    }

    public static Vertex getClosestNode(List<Vertex> input)
    {
	double minDist = Double.POSITIVE_INFINITY;
	int index = 0;
	int foundIndex = 0;
	while (index < input.size())
	{
	    if (input.get(index).minDistance < minDist)
	    {
		minDist = input.get(index).minDistance;
		foundIndex = index;
		// System.out.println("Found index: " + foundIndex + " -- min dist: " + minDist);
	    }
	    index++;
	    // System.out.println(Dijkstra.getShortestPathTo(input.get(foundIndex)));
	    // path.addAll(Dijkstra.getShortestPathTo(input.get(foundIndex)));
	}
	return input.get(foundIndex);
    }

}
