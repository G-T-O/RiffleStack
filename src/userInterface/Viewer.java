
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.BulletService;
import specifications.CharacterService;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

import data.ia.Heroes;
import data.ia.MediumMonster;
import data.ia.SmallMonster;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private ImageView smallMonsterAvatar,mediumMonsterAvatar,normalBulletAvatar,heroesAvatar;
  private Image mediumMonsterImage,smallMonsterImage,normalBulletImage;
  
  private Heroes heroes = new Heroes();
  private ArrayList<SmallMonster> smallMonsters;
  private ArrayList<MediumMonster> mediumMonsters;
  private ArrayList<BulletService>bullets,monsterBullets;

  private double xShrink,yShrink,shrink,xModifier,yModifier,radius;

  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    xShrink=1;
    yShrink=1;
    xModifier=0;
    yModifier=0;
    
  }

  @Override
  public Parent getPanel(){
    shrink=Math.min(xShrink,yShrink);
    xModifier=.01*shrink*defaultMainHeight;
    yModifier=.01*shrink*defaultMainHeight;

    //Yucky hard-conding
    
    heroesAvatar = new ImageView(heroes.getImage());
    Rectangle map = new Rectangle(-2*xModifier+shrink*defaultMainWidth,
                                  -.2*shrink*defaultMainHeight+shrink*defaultMainHeight);
    map.setFill(Color.WHITE);
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(.01*shrink*defaultMainHeight);
    map.setArcWidth(.04*shrink*defaultMainHeight);
    map.setArcHeight(.04*shrink*defaultMainHeight);
    map.setTranslateX(xModifier);
    map.setTranslateY(yModifier);
    
    Text greets = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
    						-0.1*shrink*1+shrink*40,
                           "Round 1");
    greets.setFont(new Font(.05*shrink*defaultMainHeight));
    
    Text score = new Text(-0.1*shrink*defaultMainHeight+.3*shrink*defaultMainWidth,
    					-0.1*shrink*1+shrink*40,
                           "Score: "+data.getScore());
    score.setFont(new Font(.05*shrink*defaultMainHeight));
    
    Text life = new Text(-0.1*shrink*defaultMainHeight+.1*shrink*defaultMainWidth,
			-0.1*shrink*1+shrink*40,
               "Vies: "+data.getLife());
    	life.setFont(new Font(.05*shrink*defaultMainHeight));
    
    Group panel = new Group();
    panel.getChildren().addAll(map,greets,score,life,heroesAvatar);
    heroesAvatar.setTranslateX(shrink*data.getHeroesPosition().x+shrink*xModifier-radius);
    heroesAvatar.setTranslateY(shrink*data.getHeroesPosition().y+shrink*yModifier-radius);
    heroesAvatar.setFitHeight(data.getHeroesHeight()*shrink);
    heroesAvatar.setPreserveRatio(true);
	
    
    smallMonsters = data.getSmallMonster();
    mediumMonsters = data.getMediumMonster();
    bullets = data.getBullets();
    monsterBullets = data.getMonsterBullet();
    CharacterService p;
    BulletService b;
    
    for (int i=0; i<monsterBullets.size();i++) {
    	b = monsterBullets.get(i);
    	radius=.5*Math.min(shrink*data.getBulletWidth(),shrink*data.getBulletHeight());
        normalBulletImage = new Image("file:src/images/bullets/normalBullet.png"); 
        normalBulletAvatar = new ImageView(normalBulletImage);

        normalBulletAvatar.setTranslateX(shrink*b.getPosition().x+shrink*xModifier-radius);
        normalBulletAvatar.setTranslateY(shrink*b.getPosition().y+shrink*yModifier-radius);
        normalBulletAvatar.setFitHeight(data.getBulletHeight()*shrink);
        normalBulletAvatar.setPreserveRatio(true);

        panel.getChildren().addAll(normalBulletAvatar);
    }
    for (int i=0; i<smallMonsters.size();i++){
      p=smallMonsters.get(i);
       radius=.5*Math.min(shrink*data.getSmallMonsterWidth(),shrink*data.getSmallMonsterHeight());
      smallMonsterImage = new Image("file:src/images/monsters/smallMonster.png"); 
      smallMonsterAvatar = new ImageView(smallMonsterImage);
      smallMonsterAvatar.setTranslateX(shrink*p.getPosition().x+shrink*xModifier-radius);
      smallMonsterAvatar.setTranslateY(shrink*p.getPosition().y+shrink*yModifier-radius);
      smallMonsterAvatar.setFitHeight(data.getSmallMonsterHeight()*shrink);
      smallMonsterAvatar.setPreserveRatio(true);
      
      panel.getChildren().addAll(smallMonsterAvatar);
    }
    for (int i=0; i<mediumMonsters.size();i++){
        p=mediumMonsters.get(i);
         radius=.5*Math.min(shrink*data.getSmallMonsterWidth(),shrink*data.getSmallMonsterHeight());
        mediumMonsterImage = new Image("file:src/images/monsters/mediumMonster.png"); 
        mediumMonsterAvatar = new ImageView(mediumMonsterImage);
        mediumMonsterAvatar.setTranslateX(shrink*p.getPosition().x+shrink*xModifier-radius);
        mediumMonsterAvatar.setTranslateY(shrink*p.getPosition().y+shrink*yModifier-radius);
        mediumMonsterAvatar.setFitHeight(data.getMediumMonsterHeight()*shrink);
        mediumMonsterAvatar.setPreserveRatio(true);
        panel.getChildren().addAll(mediumMonsterAvatar);
      
    }
    
    for (int i=0; i<bullets.size();i++){
        b=bullets.get(i);
         radius=.5*Math.min(shrink*data.getBulletWidth(),shrink*data.getBulletHeight());
        normalBulletImage = new Image("file:src/images/bullets/normalBullet.png"); 
        normalBulletAvatar = new ImageView(normalBulletImage);

        normalBulletAvatar.setTranslateX(shrink*b.getPosition().x+shrink*xModifier-radius);
        normalBulletAvatar.setTranslateY(shrink*b.getPosition().y+shrink*yModifier-radius);
        normalBulletAvatar.setFitHeight(data.getBulletHeight()*shrink);
        normalBulletAvatar.setPreserveRatio(true);
        panel.getChildren().addAll(normalBulletAvatar);
      }
    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }
  
  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }
}
