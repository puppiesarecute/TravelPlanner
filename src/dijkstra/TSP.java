package dijkstra;

import graphimplementation.AdjacentListGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSP
{
    public static double totalDistance = 0;
    public static Path bestPath;
    public static Path permutationBestPath;

    public static List<String> improvedGreedyFriendlyOutput(List<String> inputCities)
    {
	AdjacentListGraph graph = new AdjacentListGraph();
	List<String> citiesNames = new ArrayList<String>();
	List<Vertex> improvedGreedy = improvedGreedyOutPut(inputCities, graph);
	 citiesNames = getFullDijkstraPath(improvedGreedy, graph);
//	for (int i = 0; i < improvedGreedy.size(); i++)
//	{
//	    citiesNames.add(improvedGreedy.get(i).name);
//	}

	 System.out.println("Best Path: " + bestPath.getDistance());
	 System.out.println(bestPath);
	 System.out.println("----------------------");
	 System.out.println("----------------------");
	 System.out.println("----------------------");
	return citiesNames;

    }

    public static List<Vertex> improvedGreedyOutPut(List<String> inputCities, AdjacentListGraph graph)
    {
	List<Vertex> greedyResult = new ArrayList<Vertex>();
	greedyResult = greedyAlgorithm(inputCities, graph);

	// call do 2 opt
	List<Vertex> result2Opt = new ArrayList<Vertex>(greedyResult); // use result of greedy to start with 2opt

	// convert to array
	Vertex[] resultTwoOptArray = new Vertex[result2Opt.size()];
	for (int i = 0; i < result2Opt.size(); i++)
	{
	    resultTwoOptArray[i] = result2Opt.get(i);
	}

	bestPath = doTwoOpt(resultTwoOptArray, 1, resultTwoOptArray.length - 1, graph);
	totalDistance = bestPath.getDistance();

	// convert bestPath's path to list
	List<Vertex> bestPathResult = new ArrayList<Vertex>();
	for (int i = 0; i < bestPath.getPath().length; i++)
	{
	    bestPathResult.add(bestPath.getPath()[i]);
	}

	return bestPathResult;
    }

    public static List<Vertex> greedyAlgorithm(List<String> inputCities, AdjacentListGraph graph)
    {
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

	// add return city CPH
	result.add(graph.nodes.get(11));

	// convert result list to array
	Vertex[] array = new Vertex[result.size()];
	for (int i = 0; i < result.size(); i++)
	{
	    array[i] = result.get(i);
	}

	// calc total dist
	totalDistance = calculatePathDistance(array, graph);

	// test call permutation
	// TODO does not work - needs fixing
	// Path p = new Path(resultTwoOptArray);
	// p = doPermutation(resultTwoOptArray, 1, resultTwoOptArray.length - 2, graph);
	// System.out.println("Permutation best length: " + p.getDistance());
	// System.out.println("Permutation best path: " + p);

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

	    // // get distance to the last city before going back to CPH
	    // double distToLastCity = inputCities.get(0).minDistance;
	    //
	    // // get distance from that last city to CPH
	    // double distToCPH = getDistanceBetweenVertex(inputCities.get(0), graph.nodes.get(11), graph);
	    //
	    // totalDistance = totalDistance + distToLastCity + distToCPH;
	}
	else if (inputCities.size() > 1)
	{
	    Vertex nextCity = getClosestNode(inputCities);
	    inputCities.remove(nextCity);
	    origin = nextCity;
	    // totalDistance += nextCity.minDistance;
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
		double dist = getDistanceBetweenVertex(input[i], input[i + 1], graph);
		// System.out.println("Distance from " + input[i] + " to " + input[i+1] + " is: ---- " + dist);
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
		if (newDistance < totalDistance)
		{
		    totalDistance = newDistance;
		    result = newPath;
		    result.setDistance(newDistance);
		     System.out.println("Better dist: " + newDistance);
		     System.out.println("Better path: " + result);
		}
		else
		{
		    // if new distance is not better, swap it back
		    swap(input, i, j);
		}
	    }
	}
	return result;
    }

    public static Path doPermutation(Vertex[] input, int k, int m, AdjacentListGraph graph)
    {
	if (input.length > 8)
	{
	    throw new IllegalArgumentException("Input size is too large for permutation");
	}
	else
	{
	    if (k == m)
	    {
		double newDistance = calculatePathDistance(input, graph);
		if (newDistance < bestPath.getDistance())
		{
		    permutationBestPath = new Path(input);
		    permutationBestPath.setDistance(newDistance);
		    // System.out.println("Permutation best Path length: " + newDistance);
		    // System.out.println("Permutation best Path: " + bestPath);
		}
	    }
	    else
	    {
		for (int i = k; i <= m; i++)
		{
		    swap(input, k, i);
		    permutationBestPath = doPermutation(input, k + 1, m, graph);
		    swap(input, k, i);
		}
	    }
	}
	return permutationBestPath;
    }

    public static List<String> getFullDijkstraPath(List<Vertex> input, AdjacentListGraph graph)
    {
	List<String> result = new ArrayList<String>();

	for (int j = 0; j < input.size() - 1; j++)
	{
	    // clean up input
	    for (int i = 0; i < input.size(); i++)
	    {
		input.get(i).reset();
	    }

	    Vertex source = input.get(j);
	    Dijkstra.computePaths(source);
	    List<Vertex> path = Dijkstra.getShortestPathTo(input.get(j + 1));
	    result.add(source.name);
	    for (int i = 1; i < path.size()-1; i++)
	    {
		result.add(path.get(i).name);
	    }
	}
	result.add(input.get(input.size()-1).name);
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
