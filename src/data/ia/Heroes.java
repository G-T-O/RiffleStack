package data.ia;

import javafx.scene.image.Image;
import specifications.CharacterService;
import tools.Position;

public class Heroes extends Personnage {

	@Override
	public Position getPosition() {return position;}

	@Override
	public CharacterService.MOVE getAction() {return CharacterService.MOVE.RIGHT;}

	@Override
	public void setPosition(Position p) {position =p;}
	


}
