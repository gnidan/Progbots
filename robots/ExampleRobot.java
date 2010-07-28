import model.*;
import java.util.Random;

public class ExampleRobot implements Robot
{
  private RobotAPI api;

  public ExampleRobot()
  {
  }

  public void setAPI(RobotAPI api)
  {
    this.api = api;

    api.rename("Example Robot");
  }

  public String getName()
  {
    return api.getName();
  }

  public void run()
  {
    while(true)
    {
      Random r = new Random();
      for(int i = 0; i < r.nextInt(5); i++)
        api.move();

      api.fire();

      api.rotate(r.nextInt(360) - 180);

    }
  }
}
