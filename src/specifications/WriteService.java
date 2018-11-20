
package specifications;

import tools.Position;
import tools.Sound;

import java.util.ArrayList;

import data.ia.Boss;
import data.ia.MediumMonster;
import data.ia.SmallMonster;

public interface WriteService {
  public void setHeroesPosition(Position p);
  public void setStepNumber(int n);
  
  public void addSmallMonster(Position p);
  public void addMediumMonster(Position p);
  public void addBossMonster(Position p);
  public void addBullets(Position p);
  public void addMonsterBullets(Position p);
  
  public void setSmallMonsters(ArrayList<SmallMonster> smallMonsters);
  public void setMediumMonsters(ArrayList<MediumMonster> mediumMonsters);
  public void setBossMonsters(Boss bossMonsters);
  
  public void setMonsterBullets(ArrayList<BulletService>bullet);
  public void setBullets(ArrayList<BulletService>bullets);
  public void setSoundEffect(Sound.SOUND s);
  public void addScore(int score);
  public void addLife(int life);
  public void removeLife(int life);
}
