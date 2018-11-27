
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.Sound;

import specifications.DataService;
import specifications.BulletService;
import specifications.CharacterService;
import data.ia.SimpleBullet;
import data.ia.SimpleMonsterBullet;
import data.ia.SmallMonster;
import data.ia.MediumMonster;
import data.ia.Boss;

import java.util.ArrayList;

public class Data implements DataService {
	private Position heroesPosition;
	private Position mediumMonsterPosition;
	private int stepNumber, score, life;
	private ArrayList<SmallMonster> smallMonsters;
	private ArrayList<MediumMonster> mediumMonsters;
	private Boss bossMonsters;
	private ArrayList<BulletService> bullets;
	private ArrayList<BulletService> monsterBullets;
	private double heroesWidth, heroesHeight, smallMonsterWidth, smallMonsterHeight, mediumMonsterHeight,
			mediumMonsterWidth;
	private Sound.SOUND sound;

	public Data() {
	}

	@Override
	public void init() {
		heroesPosition = new Position(HardCodedParameters.heroesStartX, HardCodedParameters.heroesStartY);
		smallMonsters = new ArrayList<SmallMonster>();
		mediumMonsters = new ArrayList<MediumMonster>();
		bullets = new ArrayList<BulletService>();
		monsterBullets = new ArrayList<BulletService>();
		stepNumber = 0;
		score = 0;
		life = 10;
		heroesHeight = HardCodedParameters.heroesHeight;
		heroesWidth = HardCodedParameters.heroesWidth;
		smallMonsterHeight = HardCodedParameters.smallMonsterHeight;
		smallMonsterWidth = HardCodedParameters.smallMonsterWidth;
		mediumMonsterWidth = HardCodedParameters.mediumMonsterWidth;
		mediumMonsterHeight = HardCodedParameters.mediumMonsterHeight;
		sound = Sound.SOUND.None;
	}

	@Override
	public Position getHeroesPosition() {
		return heroesPosition;
	}

	@Override
	public double getHeroesWidth() {
		return heroesWidth;
	}

	@Override
	public double getHeroesHeight() {
		return heroesHeight;
	}

	@Override
	public int getStepNumber() {
		return stepNumber;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Sound.SOUND getSoundEffect() {
		return sound;
	}

	@Override
	public void setHeroesPosition(Position p) {
		heroesPosition = p;
	}

	@Override
	public void setStepNumber(int n) {
		stepNumber = n;
	}

	@Override
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void setSoundEffect(Sound.SOUND s) {
		sound = s;
	}

	@Override
	public ArrayList<BulletService> getBullets() {
		return bullets;
	}

	@Override
	public void setBullets(ArrayList<BulletService> bullets) {
		this.bullets = bullets;
	}

	@Override
	public double getBulletWidth() {
		return HardCodedParameters.bulletWidth;
	}

	@Override
	public double getBulletHeight() {
		return HardCodedParameters.bulletHeight;
	}

	@Override
	public void addBullets(Position p) {
		bullets.add(new SimpleBullet(p));
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public void addLife(int life) {
		this.life += life;
	}

	@Override
	public void removeLife(int life) {
		this.life -= life;
	}

	@Override
	public void addSmallMonster(Position p) {
		smallMonsters.add(new SmallMonster(p));
	}

	@Override
	public void setSmallMonsters(ArrayList<SmallMonster> smallMonsters) {
		this.smallMonsters = smallMonsters;
	}

	@Override
	public ArrayList<SmallMonster> getSmallMonster() {
		return smallMonsters;
	}

	@Override
	public double getSmallMonsterWidth() {
		return smallMonsterWidth;
	}

	@Override
	public double getSmallMonsterHeight() {
		return smallMonsterHeight;
	}

	@Override
	public ArrayList<MediumMonster> getMediumMonster() {
		return mediumMonsters;
	}

	@Override
	public void setMediumMonsters(ArrayList<MediumMonster> mediumMonsters) {
		this.mediumMonsters = mediumMonsters;
	}

	@Override
	public void addMediumMonster(Position p) {
		mediumMonsters.add(new MediumMonster(p));
	}

	@Override
	public double getMediumMonsterWidth() {
		return mediumMonsterWidth;
	}

	@Override
	public double getMediumMonsterHeight() {
		return mediumMonsterHeight;
	}

	@Override
	public Boss getMonsterBoss() {
		return bossMonsters;
	}

	@Override
	public void setBossMonsters(Boss bossMonsters) {
		this.bossMonsters = bossMonsters;
	}

	@Override
	public void addBossMonster(Position p) {
		bossMonsters = new Boss(p);
	}

	@Override
	public Position getMediumMonsterPosition(MediumMonster mediumMonster) {
		return mediumMonster.getPosition();
	}

	@Override
	public double getMonsterBulletWidth() {
		return HardCodedParameters.monsterBulletWidth;
	}

	@Override
	public double getMonsterBulletHeight() {
		return HardCodedParameters.monsterBulletHeight;
	}

	@Override
	public ArrayList<BulletService> getMonsterBullet() {
		return monsterBullets;
	}

	@Override
	public void addMonsterBullets(Position p) {
		monsterBullets.add(new SimpleMonsterBullet(p));
	}

	@Override
	public void setMonsterBullets(ArrayList<BulletService> monsterBullet) {
		this.monsterBullets = monsterBullet;
	}

	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		
	}

}
