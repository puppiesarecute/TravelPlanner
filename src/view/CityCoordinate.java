package view;

public class CityCoordinate
{
    private String name;
    private int x;
    private int y;
    
    public CityCoordinate(String name)
    {
	this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
	return "CityCoordinate [name=" + name + ", x=" + x + ", y=" + y + "]";
    }
    
    
    
}
