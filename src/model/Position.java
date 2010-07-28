package model;
public class Position
{
  private double x, y;

  public Position(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public Position applyVector( double magnitude, double direction )
  {
    double diffY = magnitude * Math.sin(direction);
    double diffX = magnitude * Math.cos(direction);

    return new Position(x + diffX, y + diffY);
  }

  public double getX() { return x; }
  public double getY() { return y; }
}
