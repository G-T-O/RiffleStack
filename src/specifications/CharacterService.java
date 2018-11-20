
package specifications;

import tools.Position;
import javafx.scene.image.Image;

public interface CharacterService{
  public enum MOVE { LEFT, RIGHT, UP, R ,DOWN,SPACE };
  public Position getPosition();
  public MOVE getAction();
  public void setPosition(Position p);
  public Image getImage();
}
