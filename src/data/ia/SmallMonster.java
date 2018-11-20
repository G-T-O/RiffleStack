package data.ia;

import specifications.CharacterService;
import tools.Position;

public class SmallMonster extends Personnage{
	
	public SmallMonster(Position p){ position=p; }
	
	@Override
	public Position getPosition() {return position;}

	@Override
	public CharacterService.MOVE getAction() {return CharacterService.MOVE.LEFT;}

	@Override
	public void setPosition(Position p) {position = p;}

}
