package controller;

import model.*;
import view.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.Semaphore;

import java.awt.Graphics2D;
import java.awt.geom.*;
import javax.swing.*;

public class GameController
{
  private Game game;
  private Semaphore gameSemaphore;
  private PluginClassLoader urlLoader;
  private boolean running = false;

  public GameController(int height, int width)
  {
    game = new Game(height, width);
  }

  public Collection<RobotAPI> getRobots()
  {
    return game.getRobots();
  }

  public RobotAPI loadRobot(File file)
  {
    Robot robot = getRobotFromFile(file);

    if(robot != null)
    {
      RobotAPI api = game.newGameRobotAPI(robot);
      game.addRobot(api);

      return api;
    }
    else
    {
      return null;
    }
  }

  public void step()
  {
    game.step();
  }

  public boolean getRunning() { return running; }
  public void setRunning(boolean running) { this.running = running; }

  private Robot getRobotFromFile(File file)
  {
    Robot robot;
    try
    {
      URL[] urls = new URL[1];

      urls[0] = file.toURI().toURL();

      urlLoader = new PluginClassLoader(urls,
          GameController.class.getClassLoader());

      String klass = file.getName().split("\\.")[0];

      robot = (Robot) urlLoader.loadClass(klass).newInstance();

      return robot;

    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void setGameSemaphore(Semaphore s)
  {
    game.setGameSemaphore(s);
    gameSemaphore = s;
  }

  public void drawGameObjects(Graphics2D g)
  {
    try { gameSemaphore.acquire(); }
    catch(InterruptedException e) { }
    for(GameObject o : game.getGameObjects())
      o.draw(g);
    for(BulletAPI bullet : game.getBullets())
    {
      bullet.move();
      bullet.draw(g);
    }
    gameSemaphore.release();

  }
}
