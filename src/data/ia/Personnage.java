package data.ia;

import data.Sprites;
import specifications.CharacterService;
import tools.Position;
import javafx.scene.image.Image;

public abstract class Personnage implements CharacterService {
	protected Position position;
	private Sprites sprite = new Sprites();

	public enum MOVE {
		LEFT, RIGHT, UP, R, DOWN
	};

	public Position getPosition() {
		return position;
	}

	public abstract CharacterService.MOVE getAction();

	public abstract void setPosition(Position p);

	public Image getImage() {
		return sprite.getImage();
	}
}
