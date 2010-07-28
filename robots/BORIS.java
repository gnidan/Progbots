import model.*;

public class BORIS implements Robot
{
  private RobotAPI api;

  public BORIS()
  {
  }

  public void setAPI(RobotAPI api)
  {
    this.api = api;

    api.rename("B.O.R.I.S. the Crusher");
  }

  public String getName()
  {
    return api.getName();
  }

  public void run()
  {
    while(true)
    {
      for(int i = 0; i < 40; i++)
      {
        api.move();
        api.fire();
      }

      api.rotate(120);
    }
  }
}
