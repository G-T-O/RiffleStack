package specifications;

import specifications.CharacterService.MOVE;
import tools.Position;
import javafx.scene.image.Image;

public interface BulletService {
	  public enum MOVE { LEFT, RIGHT, UP, DOWN };
	  public Image getImage();
	  public void setImage(Image image);
	  public Position getPosition();
	  public MOVE getAction();
	  public void setPosition(Position p);
}
