package dijkstra;

import graphimplementation.AdjacentListGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSP
{
    public static double totalDistance = 0;

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
	
	List<Vertex> result = greedyAlgorithm(graph.nodes.get(11), inputNodes, graph);
	
	// test call do 2 opt
	List<Vertex> result2Opt = new ArrayList<Vertex>(result);
	result2Opt.add(graph.nodes.get(11));
	Vertex[] resultTwoOptArray = new Vertex[result2Opt.size()];
	for (int i = 0; i < result2Opt.size(); i++)
	{
	    resultTwoOptArray[i] = result2Opt.get(i);
	}
	Path bestPath = doTwoOpt(resultTwoOptArray, 1, resultTwoOptArray.length - 1, graph);
	
	System.out.println("Best Path: " + bestPath.getDistance());
	System.out.println(bestPath);
	
//	System.out.println(calculatePathDistance(resultTwoOptArray, graph));
	return result;
    }

    public static List<Vertex> greedyAlgorithm(Vertex origin, List<Vertex> inputCities, AdjacentListGraph graph)
    {
	List<Vertex> output = new ArrayList<Vertex>();
	output.add(origin);
	for (Vertex vertex : graph.nodes)
	{
	    vertex.reset();
	}
	Dijkstra.computePaths(origin);

	if (inputCities.size() == 1)
	{
	    output.add(inputCities.get(0));

	    // get distance to the last city before going back to CPH
	    double distToLastCity = inputCities.get(0).minDistance;

	    // get distance from that last city to CPH
	    double distToCPH = getDistanceBetweenVertex(inputCities.get(0), graph.nodes.get(11), graph);

	    totalDistance = totalDistance + distToLastCity + distToCPH;
	}
	else if (inputCities.size() > 1)
	{
	    Vertex nextCity = getClosestNode(inputCities);
	    inputCities.remove(nextCity);
	    origin = nextCity;
	    totalDistance += nextCity.minDistance;
	    output.addAll(greedyAlgorithm(origin, inputCities, graph));
	}

	return output;

    }

    /**
     * calculate shortest distance between source and destination vertex, the result is minDistance of destination vertex
     * @param lastCity
     * @param origin
     * @param graph
     * @return
     */
    public static double getDistanceBetweenVertex(Vertex source, Vertex destination, AdjacentListGraph graph)
    {
	for (Vertex vertex : graph.nodes)
	{
	    vertex.reset();
	}
	Dijkstra.computePaths(source);
	return destination.minDistance;
    }

    /**
     * find the vertex that has the minimum minDistance among the list of vertex
     * @param input
     * @return
     */
    public static Vertex getClosestNode(List<Vertex> input)
    {
	double minDist = input.get(0).minDistance;
	int index = 1;
	int foundIndex = 0;
	while (index < input.size())
	{
	    if (input.get(index).minDistance < minDist)
	    {
		minDist = input.get(index).minDistance;
		foundIndex = index;
	    }
	    index++;
	}
	return input.get(foundIndex);
    }

    /**
     * calculate total cost of a certain path following the order of vertex in input list
     * @param input
     * @param graph
     * @return Path object the input list is supposed to include the origin city (CPH) as start and end point, so input size should be at least 3
     */
    public static double calculatePathDistance(Vertex[] input, AdjacentListGraph graph)
    {
	double totalDistance = 0;

	if (input.length > 2)
	{
	    for (int i = 0; i < input.length - 1; i++)
	    {
		double dist = getDistanceBetweenVertex(input[i], input[i+1], graph);
//		System.out.println("Distance from " + input[i] + " to " + input[i+1] + " is: ---- " + dist);
		totalDistance += dist;
	    }
	}
	return totalDistance;
    }
    
    public static Path doTwoOpt(Vertex[] input, int startSwap, int endSwap, AdjacentListGraph graph)
    {
	Path result = new Path(input);
	double totalDistance = calculatePathDistance(input, graph);
	result.setDistance(totalDistance);
	for (int i = startSwap; i < endSwap; i++)
	{
	    for (int j = i + 1; j < endSwap; j++)
	    {
		swap(input, i, j);
		Path newPath = new Path(input);
		double newDistance = calculatePathDistance(input, graph);
		System.out.println("New dist: " + newDistance);
		if(newDistance < totalDistance)
		{
		    totalDistance = newDistance;
		    result = newPath;
		    result.setDistance(newDistance);
		    System.out.println("Better dist: " + newDistance);
		    System.out.println("Better path: " + result);
		}
	    }
	}
	return result;
    }

    private static void swap(Vertex[] input, int i, int j)
    {
	Path before = new Path(input);
	System.out.println("Before swap: " + before);
	Vertex temp = input[i];
	input[i] = input[j];
	input[j] = temp;
	Path after = new Path(input);
	System.out.println("After swap: " + after);
    }
}
