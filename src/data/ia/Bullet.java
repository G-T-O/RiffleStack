package data.ia;

import javafx.scene.image.Image;
import specifications.BulletService;
import tools.Position;

public abstract class Bullet implements BulletService {
	protected Position position;
	protected Image image;

	public abstract Position getPosition();

	public abstract BulletService.MOVE getAction();

	public abstract void setPosition(Position p);
}
