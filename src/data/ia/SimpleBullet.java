package data.ia;

import javafx.scene.image.Image;
import specifications.BulletService;
import tools.Position;

public class SimpleBullet extends Bullet {

	public SimpleBullet(Position p) {
		position = p;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public BulletService.MOVE getAction() {
		return BulletService.MOVE.RIGHT;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setImage(Image img) {
		image = img;
	}
}