package model;
import java.util.concurrent.Semaphore;
import java.awt.Graphics2D;
import java.awt.geom.*;

public final class BulletAPI extends GameObject
{
  private Game game;
  private int direction;
  private Robot owner;

  private boolean active = true;
  private int SPEED = 50;

  public BulletAPI(Game game, Object key, Robot owner, int direction)
  {
    this.game = game;
    this.owner = owner;
    this.key = key;
    this.direction = direction;
  }

  public void move()
  {

    double radians = Math.toRadians(direction);

    Position newPosition = position.applyVector( SPEED, radians );

    if( newPosition == null)
    System.out.println("huh?");

    if( newPosition.getX() > 0 && 
          newPosition.getX() < game.getWidth() &&
        newPosition.getY() > 0 && 
          newPosition.getY() < game.getHeight())
    {
      position = newPosition;
    }
    else
    {
      active = false;
    }

  }

  public boolean isActive()
  {
    return active;
  }

  /* Other system methods */
  public void draw(Graphics2D g)
  {
    int x = (int) position.getX();
    int y = (int) position.getY();

    Ellipse2D.Double p = new Ellipse2D.Double(x, y, 2, 2);

    if(active)
      g.draw(p);
  }
}
