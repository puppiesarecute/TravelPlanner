package graphimplementation;

import java.awt.EventQueue;
import view.MainWindow;

public class LeProgram
{

    public static void main(String[] args)
    {
	// for (int i = 0; i < 26; i++)
	// {
	// AdjacentListGraph alg = new AdjacentListGraph();
	// Vertex source = alg.nodes.get(i);
	//
	// Dijkstra.computePaths(source);
	// System.out.println("Source: " + source);
	// for (Vertex node : alg.nodes)
	// {
	// System.out.println("Distance to " + node + ": " + node.minDistance);
	// List<Vertex> path = Dijkstra.getShortestPathTo(node);
	// System.out.println("Shortest path: " + path);
	// }
	// }


	// Launch main window
	EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    MainWindow frame = new MainWindow();
		    frame.setVisible(true);
		    frame.setLocationRelativeTo(null);
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

}
