package model;
import java.util.concurrent.Semaphore;

import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;

public final class RobotAPI extends GameObject
{
  private int orientation;
  private Game game;
  private final int SPEED = 10;
  private final int SIZE = 20;
  private String name;
  private int health;
  private Robot robot;

  public RobotAPI(Game game, Object key)
  {
    this.game = game;
    this.key = key;
  }

  /* Publicly accessible object methods */
  public void rename(String name)
  {
    this.name = name;
  }

  public void move()
  {
    wait_for_turn();
    
    double radians = Math.toRadians(orientation);

    Position newPosition = position.applyVector( SPEED, radians );
    if( newPosition.getX() > SIZE && 
          newPosition.getX() < game.getWidth() - SIZE &&
        newPosition.getY() > SIZE && 
          newPosition.getY() < game.getHeight() - SIZE)
      position = newPosition;
    give_up_turn();
  }

  public int getOrientation()
  {
    return orientation;
  }

  public void rotate(int degrees)
  {
    for(int i = 0; i < Math.abs(degrees); i += 10)
    {
      wait_for_turn();

      if(degrees > 0)
        orientation += 10;
      else
        orientation -= 10;
      
      give_up_turn();
    }
  }

  public void fire()
  {
    wait_for_turn();

    double radians = Math.toRadians(orientation);
    
    game.fireBullet(robot, position.applyVector(SPEED, radians), orientation);

    give_up_turn();
  }
/* Information getting methods */
  public int getHealth()
  {
    return health;
  }

  public String getName()
  {
    return name;
  }

  public Position getPosition()
  {
    return position;
  }

  public Robot getRobot()
  {
    return robot;
  }

  public String toString()
  {
    if(getName() != null)
      return getName();
    else
      return "<Unnamed Robot>";
  }

  /* Restricted methods */
  public void setRobot(Object key, Robot robot)
  {
    if( !checkKey(key) )
      return;

    this.robot = robot;

  }

  public void setOrientation(Object key, int orientation)
  {
    if( !checkKey(key) )
      return;

    this.orientation = orientation;
  }


  /* Other system methods */
  public void draw(Graphics2D g)
  {
    /* Draw robot itself */
    Position position = getPosition();
    int x = (int) position.getX();
    int y = (int) position.getY();

    double radians = Math.toRadians(orientation);

    int diffX = (int) (SIZE * Math.cos( radians ));
    int diffY = (int) (SIZE * Math.sin( radians ));

    Rectangle2D.Double r = new Rectangle2D.Double(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
    Line2D.Double l = new Line2D.Double(x, y, x + diffX, y + diffY);

    g.draw(r);
    g.draw(l);


    /* Draw label */
    Font font = new Font("Dialog", Font.PLAIN, 10);

    FontMetrics metrics = g.getFontMetrics(font);
    int hgt = metrics.getHeight();
    int adv = metrics.stringWidth(getName());
    Dimension size = new Dimension(adv+2, hgt+2);
    
    g.drawString(getName(), x - 5, y + SIZE + 5);


  }

}
