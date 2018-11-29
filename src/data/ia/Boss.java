package data.ia;

import specifications.CharacterService;
import tools.Position;

public class Boss extends Personnage {
	private int life=1;

	
	public int getLife() {
		return life;
	}
	
	public void removeLife(int life) {
		this.life=this.life-life;
	}
	
	public Boss(Position p) {
		position = p;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public CharacterService.MOVE getAction() {
		return CharacterService.MOVE.UP;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}
}
