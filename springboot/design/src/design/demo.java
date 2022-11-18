package design;
interface Renderer{
    public String whatToRenderAs();
}

class VectorRenderer implements Renderer{
    @Override
     public String whatToRenderAs(){
         return "pixels";
     }
}

class RasterRenderer implements Renderer{
    @Override
     public String whatToRenderAs(){
         return "lines";
     }
}


abstract class Shape
{
    protected Renderer r;
    protected Shape(Renderer r){
        this.r = r;
    }
    public abstract String getName();
    public String toString()
    {
      return String.format("Drawing %s as %s", getName(), this.r.whatToRenderAs());
    }
}

class Triangle extends Shape
{
     public Triangle(Renderer r){
        super(r);
    }
    @Override
     public String getName()
    {
      return "Triangle";
     }
}

class Square extends Shape
{
     public Square(Renderer r){
         super(r);
    }
  @Override
  public String getName()
  {
    return "Square";
  }
//  public String toString()
//  {
//    return String.format("Drawing %s as %s", getName(), this.r.whatToRenderAs());
//  }
}

class VectorSquare extends Square
{
     public VectorSquare(){
         super(new VectorRenderer());
    }
  @Override
  public String toString()
  {
    return String.format("Drawing %s as lines", getName());
  }
}

class RasterSquare extends Square
{
     public RasterSquare(){
         super(new RasterRenderer());
    }
  @Override
  public String toString()
  {
    return String.format("Drawing %s as pixels", getName());
  }
}

// imagine VectorTriangle and RasterTriangle are here too
public class demo {
	public static void main(String [] args) {
		
		System.out.println(new Square(new RasterRenderer()).toString());
		System.out.println(new Square(new VectorRenderer()).toString());
	}
}
