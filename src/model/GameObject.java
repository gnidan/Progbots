package model;
import java.util.concurrent.Semaphore;

import java.awt.Graphics2D;
import java.awt.geom.*;

public abstract class GameObject
{
  protected Object key;
  protected Semaphore turn;
  protected Position position;

  /* Restricted methods */
  public void setPosition(Object key, Position p)
  {
    if( !checkKey(key) )
      return;

    position = p;
  }

  public Semaphore getTurnSemaphore(Object key)
  {
    if( !checkKey(key) )
      return null;

    return turn;
  }

  public void setSemaphore(Object key, Semaphore semaphore)
  {
    if( !checkKey(key) )
      return; 

    turn = semaphore;
  }

  protected boolean checkKey(Object key)
  {
    return key == this.key;
  }

  /* Private methods */
  protected void wait_for_turn()
  {
    try
    {
      turn.acquire();
    }
    catch(java.lang.InterruptedException e)
    {
    }
  }

  protected void give_up_turn()
  {
      turn.release();
  }


  public abstract void draw(Graphics2D g);
}
