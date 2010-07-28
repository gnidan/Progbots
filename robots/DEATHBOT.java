import model.*;

public class DEATHBOT implements Robot
{
  private RobotAPI api;

  public DEATHBOT()
  {
  }

  public void setAPI(RobotAPI api)
  {
    this.api = api;

    api.rename("Deathbot 50 jillion, mark 2");
  }

  public String getName()
  {
    return api.getName();
  }

  public void run()
  {
    while(true)
    {
      for(int i = 0; i < 18; i++)
        api.move();

      api.rotate(60);
    }
  }
}
