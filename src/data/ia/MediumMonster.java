package data.ia;

import specifications.CharacterService;
import tools.Position;

public class MediumMonster extends Personnage{
	
	public MediumMonster(Position p) {position=p;}
	
	@Override
	public Position getPosition() {return position;}


	@Override
	public CharacterService.MOVE getAction() {return CharacterService.MOVE.SPACE;}
	
	@Override
	public void setPosition(Position p) {position = p;}

}
