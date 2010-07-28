package model;
import java.util.concurrent.Semaphore;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Game
{
  private static final int BORDER = 20;

  private GameKey key = new GameKey();
  private Collection<GameObject> gameObjects;

  private int height, width;

  private LinkedList<BulletAPI> bulletQueue;

  private Semaphore gameSemaphore;

  public Game(int height, int width)
  {

    bulletQueue = new LinkedList<BulletAPI>();

    this.height = height;
    this.width = width;

    gameObjects = new ArrayList<GameObject>();
  }

  public void setGameSemaphore(Semaphore s)
  {
    gameSemaphore = s;
  }

  public RobotAPI newGameRobotAPI(Robot robot)
  {
    RobotAPI api = new RobotAPI(this, key);
    api.setPosition(key, randomPosition(BORDER));
    api.setOrientation(key, randomOrientation());

    api.setRobot(key, robot);
    robot.setAPI(api);

    Semaphore semaphore = new Semaphore(1, true);
    try
    {
      semaphore.acquire();
    }
    catch(InterruptedException e)
    {
    }

    api.setSemaphore(key, semaphore);

    return api;
  }

  public void addRobot(RobotAPI robot)
  {
    try { gameSemaphore.acquire(); }
    catch(InterruptedException e) { }

    gameObjects.add(robot);

    gameSemaphore.release();
  }

  public Collection<RobotAPI> getRobots()
  {
    try { gameSemaphore.acquire(); }
    catch(InterruptedException e) { }

    ArrayList<RobotAPI> robots = new ArrayList<RobotAPI>();
    for(GameObject o : gameObjects)
      if(o instanceof RobotAPI)
        robots.add((RobotAPI) o);

    gameSemaphore.release();

    return robots;
  }

  public Collection<BulletAPI> getBullets()
  {
    return bulletQueue;
  }

  public Collection<GameObject> getGameObjects()
  {
    return gameObjects;
  }

  public int getHeight()
  {
    return height;
  }

  public int getWidth()
  {
    return width;
  }

  public void step()
  {
    try { gameSemaphore.acquire(); }
    catch(InterruptedException e) { }


    for(GameObject g : gameObjects)
    {
      try{
        g.getTurnSemaphore(key).release();
        g.getTurnSemaphore(key).acquire();
      }
      catch(InterruptedException e)
      {
      }
    }

    gameSemaphore.release();
  }

  public void fireBullet(Robot robot, Position position, int direction)
  {
    BulletAPI api = new BulletAPI(this, key, robot, direction);
    api.setPosition(key, position);

    bulletQueue.add(api);
  }

  private class GameKey
  {
    public GameKey()
    {
    }
  }

  private Position randomPosition(int border)
  {
    /* range of values
     * x : border to width - border
     * y : border to height - border
     */
    Random r = new Random();
    int x = r.nextInt(width - 2 * border);
    int y = r.nextInt(height - 2 * border);

    return new Position(x, y);
  }

  private int randomOrientation()
  {
    Random r = new Random();
    return r.nextInt(36) * 10;

  }
    
}
