
package engine;

import tools.HardCodedParameters;
import tools.User;
import tools.Position;
import tools.Sound;

import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;
import specifications.BulletService;
import specifications.CharacterService;

import java.util.Timer;
import java.util.TimerTask;

import data.ia.Boss;
import data.ia.MediumMonster;
import data.ia.SmallMonster;

import java.util.Random;
import java.util.ArrayList;

public class Engine implements EngineService, RequireDataService {
	private static final double friction = HardCodedParameters.friction, heroesStep = HardCodedParameters.heroesStep,
			smallMonsterStep = HardCodedParameters.smallMonsterStep;
	private int spam = 5, spamMediumMonster = 30;
	private Random randomNum = new Random();
	private Timer engineClock;
	private DataService data;
	private Random gen;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private double heroesVX, heroesVY;
	private int smallMonsterY, mediumMonsterY;
	private boolean bossIsAlive = false;
	private ArrayList<SmallMonster> smallMonsters;
	private ArrayList<MediumMonster> mediumMonsters;
	private ArrayList<BulletService> bullets, monsterBullets;
	private Boss bossMonster;
	private int normalyArriveBoss=30;
	
	public Engine() {
	}

	@Override
	public void bindDataService(DataService service) {
		data = service;
	}

	@Override
	public void init() {
		engineClock = new Timer();
		gen = new Random();
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
		heroesVX = 0;
		heroesVY = 0;
	}

	@Override
	public void start() {
		engineClock.schedule(new TimerTask() {
			public void run() {
				
					
				if (gen.nextInt(10) < 3) {
					spawnSmallMonster();
				}

				if (spamMediumMonster <= 0) {
					spamMediumMonster = 30;
					spawnMediumMonster();
				}
				if ((data.getScore() >= 10) && (!bossIsAlive)) {
					spawnBossMonster();
				}

				if (bossIsAlive && data.getBossLife(bossMonster)>0) {

					bossMove(bossMonster);
					if (randomNum.nextInt(100) < 10) {
						data.addMonsterBullets(new Position(data.getBossMonsterPosition().x,
								data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 - 20));

						data.addMonsterBullets(new Position(data.getBossMonsterPosition().x,
								data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2));

						data.addMonsterBullets(new Position(data.getBossMonsterPosition().x,
								data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 + 20));
						if(data.getBossLife(bossMonster)<80) {
							data.addMonsterBullets(new Position(data.getBossMonsterPosition().x,
									data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 - 40));

							data.addMonsterBullets(new Position(data.getBossMonsterPosition().x,
									data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 + 40));
						}
						data.setSoundEffect(Sound.SOUND.HeroesShoot);
					}
				}
				spamMediumMonster--;
				mediumMonsterY = 50 + randomNum.nextInt(400);
				smallMonsterY = 50 + randomNum.nextInt(400);
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				smallMonsters = new ArrayList<SmallMonster>();
				mediumMonsters = new ArrayList<MediumMonster>();
				bullets = new ArrayList<BulletService>();// Heroes
				monsterBullets = new ArrayList<BulletService>();
				data.setSoundEffect(Sound.SOUND.None);
				
				

				for (SmallMonster p : data.getSmallMonster()) {
					if (p.getAction() == CharacterService.MOVE.LEFT)
						moveLeft(p);
						smallMonsters.add(p);
					if (collisionHeroesMonster(p)) {
						data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
						data.removeLife(1);
						smallMonsters.remove(p);
					} else if (isOutOfBorder(p)) {
						smallMonsters.remove(p);
						data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
					}
				}

				for (MediumMonster p : data.getMediumMonster()) {
					if (p.getAction() == CharacterService.MOVE.LEFT)
						moveLeft(p);
					if (p.getAction() == CharacterService.MOVE.SPACE) {
						moveLeft(p);
						if (randomNum.nextInt(100) < 10) {
							data.addMonsterBullets(data.getMediumMonsterPosition(p));
							data.setSoundEffect(Sound.SOUND.HeroesShoot);
						}
					}
					mediumMonsters.add(p);
					if (collisionHeroesMonster(p)) {
						data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
						data.removeLife(1);
						mediumMonsters.remove(p);
					} else if (isOutOfBorder(p)) {
						mediumMonsters.remove(p);
						data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
					}
				}
				try {
					for (BulletService b : data.getMonsterBullet()) {
						monsterBullets.add(b);
						bulletMoveLeft(b);
						if (collisionBulletHeroes(b, data.getHeroesPosition())) {
							monsterBullets.remove(b);
							data.removeLife(1);
						} else if (isOutOfBorder(b)) {
							monsterBullets.remove(b);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					for (BulletService b : data.getBullets()) {
						bullets.add(b);
						bulletMoveRight(b);
						if(bossIsAlive) {
							if(collisionBulletBoss(b,bossMonster)) {
								data.removeBossLife(1,bossMonster);
								bullets.remove(b);
								System.out.println(data.getBossLife(bossMonster));
							}
						}
						for (CharacterService p : data.getSmallMonster()) {
							if (collisionBulletMonster(b, p)) {
								data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
								data.addScore(1);
								smallMonsters.remove(p);
								data.smallMonsterKilled();
								bullets.remove(b);
							}
						}
						for (CharacterService p : data.getMediumMonster()) {
							if (collisionBulletMonster(b, p)) {
								data.setSoundEffect(Sound.SOUND.MonsterDestroyed);
								data.addScore(1);
								data.mediumMonsterKilled();
								mediumMonsters.remove(p);
								bullets.remove(b);
							}
						}

						if (isOutOfBorder(b)) {
							bullets.remove(b);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				data.setBullets(bullets);
				data.setMonsterBullets(monsterBullets);
				data.setSmallMonsters(smallMonsters);
				data.setMediumMonsters(mediumMonsters);
				data.setStepNumber(data.getStepNumber() + 1);
			}
		}, 0, HardCodedParameters.enginePaceMillis);
	}

	@Override
	public void stop() {
		engineClock.cancel();
	}

	@Override
	public void setHeroesCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT)
			moveLeft = true;
		if (c == User.COMMAND.RIGHT)
			moveRight = true;
		if (c == User.COMMAND.UP)
			moveUp = true;
		if (c == User.COMMAND.DOWN)
			moveDown = true;
		if (c == User.COMMAND.SPACE) {
			data.addBullets(new Position(data.getHeroesPosition().x + 120, data.getHeroesPosition().y + 20));
			data.setSoundEffect(Sound.SOUND.HeroesShoot);
		}
	}

	@Override
	public void releaseHeroesCommand(User.COMMAND c) {
		if (c == User.COMMAND.LEFT)
			moveLeft = false;
		if (c == User.COMMAND.RIGHT)
			moveRight = false;
		if (c == User.COMMAND.UP)
			moveUp = false;
		if (c == User.COMMAND.DOWN)
			moveDown = false;
	}

	private void updateSpeedHeroes() {
		heroesVX *= friction;
		heroesVY *= friction;
	}

	private void updateCommandHeroes() {// val1 Minx
		if (moveLeft) {
			if (data.getHeroesPosition().x > HardCodedParameters.minX) {
				heroesVX -= heroesStep;
			}
		}
		if (moveRight) {
			if (data.getHeroesPosition().x < HardCodedParameters.maxX - data.getHeroesWidth()) {
				heroesVX += heroesStep;
			}
		}
		if (moveUp) {
			if (data.getHeroesPosition().y > HardCodedParameters.minY) {
				heroesVY -= heroesStep;
			}
		}
		if (moveDown) {
			if (data.getHeroesPosition().y < HardCodedParameters.maxY) {
				heroesVY += heroesStep;
			}
		}
	}

	private void updatePositionHeroes() {
		data.setHeroesPosition(
				new Position(data.getHeroesPosition().x + heroesVX, data.getHeroesPosition().y + heroesVY));
	}

	private void spawnSmallMonster() {
		if (!bossIsAlive) {
			if (spam <= 0) {
				spam = 5;
				data.addSmallMonster(new Position(800, smallMonsterY));
			}
			spam--;
		}
	}

	private void spawnMediumMonster() {
		if (!bossIsAlive) {
			data.addMediumMonster(new Position(800, mediumMonsterY));
		}
	}

	private void spawnBossMonster() {
		if (!bossIsAlive) {
			data.addBossMonster(new Position(800, mediumMonsterY));
			bossMonster = data.getMonsterBoss();
			bossIsAlive = true;
		}
	}

	private void moveLeft(CharacterService p) {
		p.setPosition(new Position(p.getPosition().x - smallMonsterStep, p.getPosition().y));
	}

	private void moveRight(CharacterService p) {
		p.setPosition(new Position(p.getPosition().x + smallMonsterStep, p.getPosition().y));
	}

	private void moveUp(CharacterService p) {
		p.setPosition(new Position(p.getPosition().x, p.getPosition().y - smallMonsterStep));
	}

	private void bossMove(CharacterService p) {
		normalyArriveBoss--;
		if (data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 < data.getHeroesPosition().y) {
			moveDown(p);
		} else if (data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 <= data.getHeroesPosition().y + 20
				&& data.getBossMonsterPosition().y + data.getBossMonsterHeight() / 2 >= data.getHeroesPosition().y
						- 20) {
		} else {
			moveUp(p);
		}
		if(normalyArriveBoss>0) {
			moveLeft(p);
		}
	}

	private void moveDown(CharacterService p) {
		p.setPosition(new Position(p.getPosition().x, p.getPosition().y + smallMonsterStep));
	}

	private void bulletMoveRight(BulletService b) {
		b.setPosition(new Position(b.getPosition().x + smallMonsterStep, b.getPosition().y));
	}

	private void bulletMoveLeft(BulletService b) {
		b.setPosition(new Position(b.getPosition().x - smallMonsterStep - 10, b.getPosition().y));
	}

	private boolean collisionHeroesMonster(CharacterService p) {
		if ((p.getPosition().x <= data.getHeroesPosition().x + data.getHeroesWidth())
				&& (p.getPosition().x >= data.getHeroesPosition().x - data.getHeroesWidth())
				&& (p.getPosition().y <= data.getHeroesPosition().y + data.getHeroesHeight())
				&& (p.getPosition().y >= data.getHeroesPosition().y - data.getHeroesHeight())) {
			return true;
		}
		return false;
	}

	private boolean collisionBulletMonster(BulletService b, CharacterService p) {

		if 		  ((p.getPosition().x <= b.getPosition().x + data.getBulletWidth())
				&& (p.getPosition().x >= b.getPosition().x - data.getBulletWidth())
				&& (p.getPosition().y <= b.getPosition().y + data.getBulletHeight())
				&& (p.getPosition().y >= b.getPosition().y - data.getBulletHeight() * 1.5)) {
			return true;
		}
		return false;
	}
	private boolean collisionBulletBoss(BulletService b, Boss p) {

		if 		  ((p.getPosition().x  <= b.getPosition().x )
				&& (p.getPosition().x +data.getBossMonsterWidth() >= b.getPosition().x )
				&& (p.getPosition().y <= b.getPosition().y )
				&& (p.getPosition().y +data.getBossMonsterHeight() >= b.getPosition().y )) {
			return true;
		}
		return false;
	}

	private boolean collisionBulletHeroes(BulletService b, Position position) {

		if 		  ((data.getHeroesPosition().x <= b.getPosition().x + data.getBulletWidth())
				&& (data.getHeroesPosition().x +data.getHeroesWidth()/2 >= b.getPosition().x - data.getBulletWidth())
				&& (data.getHeroesPosition().y <= b.getPosition().y + data.getBulletHeight())
				&& (data.getHeroesPosition().y >= b.getPosition().y - data.getHeroesHeight())) {
			return true;
		}
		return false;
	}

	private boolean isOutOfBorder(CharacterService p) {
		if (p.getPosition().x < HardCodedParameters.minX)
			return true;
		return false;
	}

	private boolean isOutOfBorder(BulletService b) {

		if ((b.getPosition().x > HardCodedParameters.maxX) || (b.getPosition().x < HardCodedParameters.minX))
			return true;
		return false;
	}

}
