
package specifications;

import tools.User;

public interface EngineService{
  public void init();
  public void start();
  public void stop();
  public void setHeroesCommand(User.COMMAND c);
  public void releaseHeroesCommand(User.COMMAND c);
}
