package view;

import controller.*;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Arena extends JPanel implements Runnable
{

  private static final int FRAMES_PER_SECOND = 50;
  private GameController gameController;

  private int x = 10, y = 10;
  Thread animationThread;
  public Arena(GameController gameController)
  {
    super();

    this.gameController = gameController;

    animationThread = new Thread(this);
    animationThread.start();
  }

  public void paintChildren(Graphics g)
  {
    Graphics2D g2d = (Graphics2D) g;
    

    gameController.drawGameObjects(g2d);
  }

  public void run() {
    while(true)
    {
      repaint();
    try
    {
      Thread.sleep(100);
    }
    catch(InterruptedException e)
    {
    }

      if(gameController.getRunning())
        gameController.step();

    }
  }
}
