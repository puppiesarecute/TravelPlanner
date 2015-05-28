package dijkstra;

import java.util.List;

public class Path
{
    Vertex target;
    double minDistance;
    List<Vertex> path;

    public Path(Vertex target, double minDistance, List<Vertex> path)
    {
	this.target = target;
	this.minDistance = minDistance;
	this.path = path;
    }
}
