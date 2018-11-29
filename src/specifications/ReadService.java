
package specifications;

import tools.Position;
import tools.Sound;

import java.util.ArrayList;

import data.ia.Boss;
import data.ia.MediumMonster;
import data.ia.SimpleMonsterBullet;
import data.ia.SmallMonster;

public interface ReadService {
  public Position getHeroesPosition();
  public Position getMediumMonsterPosition(MediumMonster mediumMonster);
  public Position getBossMonsterPosition();
  public double getHeroesWidth();
  public double getHeroesHeight();
  public int getHeroesChoice();
  public double getSmallMonsterWidth();
  public double getSmallMonsterHeight();
  public double getMediumMonsterWidth();
  public double getMediumMonsterHeight();
  public double getBossMonsterWidth();
  public double getBossMonsterHeight();
  public double getBulletWidth();
  public double getBulletHeight();
  public double getMonsterBulletWidth();
  public double getMonsterBulletHeight();
  
  public int getStepNumber();
  public int getScore();
  public int getLife();
  public ArrayList <SmallMonster> getSmallMonster();
  public ArrayList <MediumMonster> getMediumMonster();
  public Boss getMonsterBoss();
  public ArrayList <BulletService>getMonsterBullet();
  public ArrayList <BulletService>getBullets();
  public Sound.SOUND getSoundEffect();
  public int getCountSmallMonsterKilled();
  public int getCountMediumMonsterKilled();
  public int getBossLife(Boss boss);
}
