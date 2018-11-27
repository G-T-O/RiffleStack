
package tools;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class HardCodedParameters {
  //---HARD-CODED-PARAMETERS---//
  public static String defaultParamFileName = "in.parameters";
  public static final int defaultWidth = 800, defaultHeight = 600,
                          heroesStartX = 80, heroesStartY = 200,  heroesStep = 10,
                          smallMonsterStep = 10,
  						  mediumMonsterStep = 10,monsterBulletWidth=30,monsterBulletHeight=30;
;
  
  public static final Image smallMonsterImage = new Image("file:src/images/monsters/smallMonster.png"); 
  public static final Image mediumMonsterImage = new Image("file:src/images/monsters/mediumMonster.png"); 
  public static final Image bossImage = new Image("file:src/images/monsters/boss1.png");
  public static final Image heroesImage = new Image("file:src/images/heroes/SS1_1.png");
  public static final Image bulletImage = new Image("file:src/images/bullets/normalBullet.png");
  
  public static final ImageView smallMonsterAvatar = new ImageView(smallMonsterImage);
  public static final ImageView mediumMonsterAvatar = new ImageView(mediumMonsterImage);
  public static final ImageView bossMonsterAvatar = new ImageView(bossImage);
  public static final ImageView heroesAvatar = new ImageView(heroesImage);
  public static final ImageView bulletAvatar = new ImageView(bulletImage);
  
  public static final double smallMonsterHeight = smallMonsterImage.getHeight();
  public static final double smallMonsterWidth = smallMonsterImage.getWidth();
  
  public static final double mediumMonsterHeight = mediumMonsterImage.getHeight();
  public static final double mediumMonsterWidth = mediumMonsterImage.getWidth();
  
  public static final double bossMonsterHeight =bossImage.getHeight();
  public static final double bossMonsterWidth = bossImage.getWidth();
  
  public static final double heroesHeight = heroesImage.getHeight();
  public static final double heroesWidth = heroesImage.getWidth();
  
  public static final double bulletHeight = bulletImage.getHeight();
  public static final double bulletWidth = bulletImage.getWidth();
  
  //public static final int 
  public static final int enginePaceMillis = 100,
                          spriteSlowDownRate = 7;
  public static final double friction = 0.50;
  public static final double resolutionShrinkFactor = 0.95,
                             userBarShrinkFactor = 0.25,
                             menuBarShrinkFactor = 0.5,
                             logBarShrinkFactor = 0.15,
                             logBarCharacterShrinkFactor = 0.1175,
                             logBarCharacterShrinkControlFactor = 0.01275,
                             menuBarCharacterShrinkFactor = 0.175;
  public static final int displayZoneXStep = 5,
                          displayZoneYStep = 5,
                          displayZoneXZoomStep = 5,
                          displayZoneYZoomStep = 5;
  public static final double displayZoneAlphaZoomStep = 0.98;

  //---MISCELLANOUS---//
  public static final Object loadingLock = new Object();
  public static final String greetingsZoneId = String.valueOf(0xED1C7E),
                             simulatorZoneId = String.valueOf(0x51E77E);
  public static final double minX=-100,maxX=800;
  public static <T> T instantiate(final String className, final Class<T> type){
    try{
      return type.cast(Class.forName(className).newInstance());
    } catch(final InstantiationException e){
      throw new IllegalStateException(e);
    } catch(final IllegalAccessException e){
      throw new IllegalStateException(e);
    } catch(final ClassNotFoundException e){
      throw new IllegalStateException(e);
    }
  }
}
