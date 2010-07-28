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
    api.rotate(90);
    api.rotate(-90);
  }
}
