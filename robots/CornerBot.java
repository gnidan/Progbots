import model.*;

public class CornerBot implements Robot
{
  private RobotAPI api;

  public CornerBot()
  {
  }

  public void setAPI(RobotAPI api)
  {
    this.api = api;

    api.rename("CornerBot");
  }

  public String getName()
  {
    return api.getName();
  }

  public void run()
  {
    int orientation = api.getOrientation();

    if(orientation < 180)
      api.rotate(-orientation);
    else
      api.rotate(360 - orientation);

    for(int i = 0; i < 50; i++)
      api.move();

    orientation = api.getOrientation();
    api.rotate(90);

    for(int i = 0; i < 50; i++)
      api.move();

    api.rotate(90);

    while(true)
    {
      for(int i = 0; i < 10; i+= 1)
      {
        api.rotate(10);
        api.fire();
      }
      for(int i = 0; i < 10; i+= 1)
      {
        api.rotate(-10);
        api.fire();
      }
    }
  }
}
