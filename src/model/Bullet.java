package model;
import java.util.concurrent.Semaphore;

public class Bullet implements Runnable
{
  private BulletAPI api;

  public Bullet(BulletAPI api)
  {
    this.api = api;
  }

  public void run()
  {
    while(api.isActive())
      api.move();
  }
}
