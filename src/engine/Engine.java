
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

import data.ia.MediumMonster;
import data.ia.SmallMonster;

import java.util.Random;
import java.util.ArrayList;

public class Engine implements EngineService, RequireDataService{
  private static final double friction=HardCodedParameters.friction,
                              heroesStep=HardCodedParameters.heroesStep,
                              smallMonsterStep=HardCodedParameters.smallMonsterStep;
  int val1=50;
  int val2=60;
  int val3=80;
  int val4=420;
  int val5=30;
  int i=0;
  private int spam = 5;
  private int spamMediumMonster =30;
  private int spamMonsterBullet =50;
  private Random randomNum = new Random();
  private Timer engineClock;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private boolean moveLeft,moveRight,moveUp,moveDown;
  private double heroesVX,heroesVY;
  private int smallMonsterY,mediumMonsterY;
  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
    engineClock = new Timer();
    command = User.COMMAND.NONE;
    gen = new Random();
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    heroesVX = 0;
    heroesVY = 0;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
 
        if (gen.nextInt(10)<3) { 
        	spawnSmallMonster();
        }

      	  if (spamMediumMonster<=0) {
      		spamMediumMonster=30;
    		  spawnMediumMonster();
    	  }
      	spamMediumMonster--;
      	mediumMonsterY=50+randomNum.nextInt(400);
      	smallMonsterY=50+randomNum.nextInt(400);
        updateSpeedHeroes();
        updateCommandHeroes();
        updatePositionHeroes();

        ArrayList<SmallMonster> smallMonsters = new ArrayList<SmallMonster>();
        ArrayList<MediumMonster> mediumMonsters = new ArrayList<MediumMonster>();
        ArrayList<BulletService> bullets = new ArrayList<BulletService>();// Heroes 
        ArrayList<BulletService> monsterBullets = new ArrayList<BulletService>();
        

        data.setSoundEffect(Sound.SOUND.None);

        
        for (SmallMonster p:data.getSmallMonster()){
          if (p.getAction()==CharacterService.MOVE.LEFT) moveLeft(p);
          		smallMonsters.add(p);
          if (collisionHeroesMonster(p)){
              data.setSoundEffect(Sound.SOUND.HeroesGotHit);
              data.removeLife(1);
              smallMonsters.remove(p);
            } else if(isOutOfBorder(p)) {
          	  smallMonsters.remove(p);
          	  data.setSoundEffect(Sound.SOUND.HeroesGotHit);
            }
          }
        
        for (MediumMonster p:data.getMediumMonster()){
            if (p.getAction()==CharacterService.MOVE.LEFT) moveLeft(p);
            if (p.getAction()==CharacterService.MOVE.SPACE) {
            		moveLeft(p);
                	
                	if(randomNum.nextInt(100)<10) {
                	data.addMonsterBullets(data.getMediumMonsterPosition(p));
                	data.setSoundEffect(Sound.SOUND.HeroesShoot);
                	}
                
            }
            		mediumMonsters.add(p);
            if (collisionHeroesMonster(p)){
                data.setSoundEffect(Sound.SOUND.HeroesGotHit);
                data.removeLife(1);
                mediumMonsters.remove(p);
              } else if(isOutOfBorder(p)) {
            	  mediumMonsters.remove(p);
            	  data.setSoundEffect(Sound.SOUND.HeroesGotHit);
              }
            }
        	try {
        		for(BulletService b:data.getMonsterBullet()) { // MediumMonsterBullet -1
        			monsterBullets.add(b);
        			bulletMoveLeft(b);
        			if(collisionBulletHeroes(b,data.getHeroesPosition())) {
        				monsterBullets.remove(b);
        				data.removeLife(1);
        			}else if(isOutOfBorder(b)) {
        				monsterBullets.remove(b);
        			}
        		}
        		}catch(Exception e) {
              	  e.printStackTrace();
                }
        	

          try {          
          for (BulletService b:data.getBullets()) {
            	bullets.add(b);
          	    bulletMoveRight(b);

          	for(CharacterService p:data.getSmallMonster()) {
          		if(collisionBulletMonster(b,p)) {
          			data.addScore(1);
      				smallMonsters.remove(p);
      				bullets.remove(b);
      			}
          	}
          		for(CharacterService p:data.getMediumMonster()) {
          			if(collisionBulletMonster(b,p)) {
          				data.addScore(1);
          				mediumMonsters.remove(p);
          				bullets.remove(b);
          			}
          		}
          	
          	if(isOutOfBorder(b)) 
          	{bullets.remove(b);}	
          }
          }catch(Exception e) {
        	  e.printStackTrace();
          }
          

          data.setBullets(bullets);
          data.setMonsterBullets(monsterBullets);
          data.setSmallMonsters(smallMonsters);
          data.setMediumMonsters(mediumMonsters);
          data.setStepNumber(data.getStepNumber()+1);
        }
      },0,HardCodedParameters.enginePaceMillis);
    }
  @Override
  public void stop(){
    engineClock.cancel();
  }

  @Override
  public void setHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=true;
    if (c==User.COMMAND.RIGHT) moveRight=true;
    if (c==User.COMMAND.UP) moveUp=true;
    if (c==User.COMMAND.DOWN) moveDown=true;
    if (c==User.COMMAND.SPACE) {
    
    	data.addBullets(new Position(data.getHeroesPosition().x+120,data.getHeroesPosition().y+20));
    	data.setSoundEffect(Sound.SOUND.HeroesShoot);
    }
  }
  
  @Override
  public void releaseHeroesCommand(User.COMMAND c){
    if (c==User.COMMAND.LEFT) moveLeft=false;
    if (c==User.COMMAND.RIGHT) moveRight=false;
    if (c==User.COMMAND.UP) moveUp=false;
    if (c==User.COMMAND.DOWN) moveDown=false;
  }

  private void updateSpeedHeroes(){
    heroesVX*=friction;
    heroesVY*=friction;
  }

  private void updateCommandHeroes(){//val1 Minx
    if (moveLeft){if(data.getHeroesPosition().x>val1){heroesVX-=heroesStep;}}
    if (moveRight){if(data.getHeroesPosition().x<HardCodedParameters.defaultWidth-val2){heroesVX+=heroesStep;}} 
    if (moveUp){if(data.getHeroesPosition().y>val3){ heroesVY-=heroesStep;}}
    if (moveDown){if(data.getHeroesPosition().y<val4){heroesVY+=heroesStep;}} 
  }
  
  private void updatePositionHeroes(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x+heroesVX,data.getHeroesPosition().y+heroesVY));
  }

  private void spawnSmallMonster(){
   if(spam<=0) {
	   spam=5;
    data.addSmallMonster(new Position(800,smallMonsterY));
   }
   spam--;
  }
  
  private void spawnMediumMonster() {

		  System.out.println("spawn medium");
		  data.addMediumMonster(new Position(800,mediumMonsterY));

  }

  private void moveLeft(CharacterService p){
    p.setPosition(new Position(p.getPosition().x-smallMonsterStep,p.getPosition().y));
	  
  }

  private void moveRight(CharacterService p){
    p.setPosition(new Position(p.getPosition().x+smallMonsterStep,p.getPosition().y));
  }

  private void moveUp(CharacterService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y-smallMonsterStep));
  }

  private void moveDown(CharacterService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y+smallMonsterStep));
  }
  
  private void bulletMoveRight(BulletService b) {
	  b.setPosition(new Position(b.getPosition().x+smallMonsterStep,b.getPosition().y));
  }  
  private void bulletMoveLeft(BulletService b) {
	  b.setPosition(new Position(b.getPosition().x-smallMonsterStep-10,b.getPosition().y));
  }

  private boolean collisionHeroesMonster(CharacterService p){
	  if ((p.getPosition().x <= data.getHeroesPosition().x+data.getHeroesWidth()) && (p.getPosition().x>=data.getHeroesPosition().x-data.getHeroesWidth())
		&& (p.getPosition().y<=data.getHeroesPosition().y+data.getHeroesHeight()) && (p.getPosition().y>=data.getHeroesPosition().y-data.getHeroesHeight())) {
		  return true;
	  }
	  return false;
  }
  
  private boolean collisionBulletMonster(BulletService b,CharacterService p ){
		 
	  if  ((p.getPosition().x <= b.getPosition().x+data.getBulletWidth()) && (p.getPosition().x>=b.getPosition().x-data.getBulletWidth())
			&& (p.getPosition().y<=b.getPosition().y+data.getBulletHeight()) && (p.getPosition().y>=b.getPosition().y-data.getBulletHeight()*1.5)) {		  
		  return true;
			  }
			  return false;
  }
  private boolean collisionBulletHeroes(BulletService b,Position position) {
	  
	  if  ((position.x <= b.getPosition().x+data.getBulletWidth()/2) && (position.x>=b.getPosition().x-data.getBulletWidth()*1.7)
				&& (position.y<=b.getPosition().y+data.getBulletHeight()/2) && (position.y>=b.getPosition().y-data.getHeroesHeight())) {		  
		  return true;
			  }
			  return false;
  }
  
  
  private boolean isOutOfBorder(CharacterService p) {		
	  if(p.getPosition().x <HardCodedParameters.minX) 
		  return true;
	  return false;
  }
  
  private boolean isOutOfBorder(BulletService b) {

	  if((b.getPosition().x >HardCodedParameters.maxX)||(b.getPosition().x <HardCodedParameters.minX)) 
		  return true;
	  return false;
  }

}
