
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
import java.util.ArrayList;
import data.ia.Boss;
import data.ia.Heroes;
import data.ia.MediumMonster;
import data.ia.SmallMonster;

public class Viewer implements ViewerService, RequireReadService {
	private static final int spriteSlowDownRate = HardCodedParameters.spriteSlowDownRate;
	private static final double defaultMainWidth = HardCodedParameters.defaultWidth,
			defaultMainHeight = HardCodedParameters.defaultHeight;
	private ReadService data;
	private ImageView spaceBGView = new ImageView(new Image("file:src/images/SpaceBackground.png")), smallMonsterAvatar,
			mediumMonsterAvatar, normalBulletAvatar, bossMonsterAvatar, heroesAvatar;
	private Image bossMonsterImage, mediumMonsterImage, smallMonsterImage, normalBulletImage;
	private Heroes heroes = new Heroes();
	private Boss bossMonster;
	private ArrayList<SmallMonster> smallMonsters;
	private ArrayList<MediumMonster> mediumMonsters;
	private ArrayList<BulletService> bullets, monsterBullets;
	private Group panel;
	private Text life;
	private double xShrink, yShrink, shrink, xModifier, yModifier, radius;

	public Viewer() {
	}

	@Override
	public void bindReadService(ReadService service) {
		data = service;
	}

	@Override
	public void init() {
		xShrink = 1;
		yShrink = 1;
		xModifier = 0;
		yModifier = 0;
	}

	@Override
	public Parent getPanel() {
		shrink = Math.min(xShrink, yShrink);
		xModifier = .01 * shrink * defaultMainHeight;
		yModifier = .01 * shrink * defaultMainHeight;
		spaceBGView.setTranslateX(0);
		spaceBGView.setTranslateY(0);
		spaceBGView.setPreserveRatio(true);
		spaceBGView.toBack();
		// Yucky hard-conding
		Rectangle map = new Rectangle(-2 * xModifier + shrink * defaultMainWidth,
				-.2 * shrink * defaultMainHeight + shrink * defaultMainHeight);

		String topText = "-fx-text-fill: #107BED;"
				+ "-fx-effect: dropshadow( one-pass-box , rgba(	230, 241, 253,0.9) , 1, 5.0 , 0 , 1 );"
				+ "-fx-font-size: 24px;";

		Text name = new Text(-0.1 * shrink * defaultMainHeight + .1 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 40, "Player: " + data.getPlayerName());
		name.setFont(new Font(.05 * shrink * defaultMainHeight));
		name.setStyle(topText);
		name.setFill(Color.WHITE);

		Text score = new Text(-0.1 * shrink * defaultMainHeight + .45 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 40, "Score: " + data.getScore());
		score.setFont(new Font(.05 * shrink * defaultMainHeight));
		score.setStyle(topText);
		score.setFill(Color.WHITE);
		if(data.getLife()>0) {
		life = new Text(-0.1 * shrink * defaultMainHeight + .65 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 40, "Life: " + data.getLife());
		life.setFont(new Font(.05 * shrink * defaultMainHeight));
		life.setStyle(topText);
		life.setFill(Color.WHITE);
		}else {
			life = new Text(-0.1 * shrink * defaultMainHeight + .65 * shrink * defaultMainWidth,
					-0.1 * shrink * 1 + shrink * 40, "Life: 0");
			life.setFont(new Font(.05 * shrink * defaultMainHeight));
			life.setStyle(topText);
			life.setFill(Color.WHITE);
		}
		Text greets = new Text(-0.1 * shrink * defaultMainHeight + .9 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 40, "Round 1");
		greets.setFont(new Font(.05 * shrink * defaultMainHeight));
		greets.setStyle(topText);
		greets.setFill(Color.WHITE);

		//addsAvatar.setPreserveRatio(true);
		panel = new Group();
		panel.getChildren().addAll(spaceBGView, name, greets, score, life);
		if (data.getLife() > 0) {
			heroes.setImage(data.getHeroesChoice());
			heroesAvatar = new ImageView(heroes.getImage());
			heroesAvatar.setTranslateX(shrink * data.getHeroesPosition().x + shrink * xModifier - radius);
			heroesAvatar.setTranslateY(shrink * data.getHeroesPosition().y + shrink * yModifier - radius);
			heroesAvatar.setFitHeight(data.getHeroesHeight() * shrink);
			heroesAvatar.setPreserveRatio(true);
			panel.getChildren().addAll(heroesAvatar);

		smallMonsters = data.getSmallMonster();
		mediumMonsters = data.getMediumMonster();
		bossMonster = data.getMonsterBoss();
		bullets = data.getBullets();
		monsterBullets = data.getMonsterBullet();

		CharacterService p;
		BulletService b;
		try {
			if (data.getBossLife(bossMonster) > 0) {
				bossMonsterImage = new Image("file:src/images/monsters/boss1.png");
				bossMonsterAvatar = new ImageView(bossMonsterImage);
				bossMonsterAvatar.setTranslateX(shrink * bossMonster.getPosition().x + shrink * xModifier - radius);
				bossMonsterAvatar.setTranslateY(shrink * bossMonster.getPosition().y + shrink * yModifier - radius);
				bossMonsterAvatar.setFitHeight(data.getBossMonsterHeight() * shrink);
				bossMonsterAvatar.setPreserveRatio(true);
				panel.getChildren().addAll(bossMonsterAvatar);
			}
		} catch (Exception e) {

		}

		for (int i = 0; i < monsterBullets.size(); i++) {
			b = monsterBullets.get(i);
			radius = .5 * Math.min(shrink * data.getBulletWidth(), shrink * data.getBulletHeight());
			normalBulletImage = new Image("file:src/images/bullets/normalMonsterBullet.png");
			normalBulletAvatar = new ImageView(normalBulletImage);
			normalBulletAvatar.setTranslateX(shrink * b.getPosition().x + shrink * xModifier - radius);
			normalBulletAvatar.setTranslateY(shrink * b.getPosition().y + shrink * yModifier - radius);
			normalBulletAvatar.setFitHeight(data.getBulletHeight() * shrink);
			normalBulletAvatar.setPreserveRatio(true);
			panel.getChildren().addAll(normalBulletAvatar);
		}
		for (int i = 0; i < smallMonsters.size(); i++) {
			p = smallMonsters.get(i);
			radius = .5 * Math.min(shrink * data.getSmallMonsterWidth(), shrink * data.getSmallMonsterHeight());
			smallMonsterImage = new Image("file:src/images/monsters/smallMonster.png");
			smallMonsterAvatar = new ImageView(smallMonsterImage);
			smallMonsterAvatar.setTranslateX(shrink * p.getPosition().x + shrink * xModifier - radius);
			smallMonsterAvatar.setTranslateY(shrink * p.getPosition().y + shrink * yModifier - radius);
			smallMonsterAvatar.setFitHeight(data.getSmallMonsterHeight() * shrink);
			smallMonsterAvatar.setPreserveRatio(true);
			panel.getChildren().addAll(smallMonsterAvatar);
		}
		for (int i = 0; i < mediumMonsters.size(); i++) {
			p = mediumMonsters.get(i);
			radius = .5 * Math.min(shrink * data.getSmallMonsterWidth(), shrink * data.getSmallMonsterHeight());
			mediumMonsterImage = new Image("file:src/images/monsters/mediumMonster.png");
			mediumMonsterAvatar = new ImageView(mediumMonsterImage);
			mediumMonsterAvatar.setTranslateX(shrink * p.getPosition().x + shrink * xModifier - radius);
			mediumMonsterAvatar.setTranslateY(shrink * p.getPosition().y + shrink * yModifier - radius);
			mediumMonsterAvatar.setFitHeight(data.getMediumMonsterHeight() * shrink);
			mediumMonsterAvatar.setPreserveRatio(true);
			panel.getChildren().addAll(mediumMonsterAvatar);
		}

		for (int i = 0; i < bullets.size(); i++) {
			b = bullets.get(i);
			radius = .5 * Math.min(shrink * data.getBulletWidth(), shrink * data.getBulletHeight());
			normalBulletImage = new Image("file:src/images/bullets/normalBullet.png");
			normalBulletAvatar = new ImageView(normalBulletImage);
			normalBulletAvatar.setTranslateX(shrink * b.getPosition().x + shrink * xModifier - radius);
			normalBulletAvatar.setTranslateY(shrink * b.getPosition().y + shrink * yModifier - radius);
			normalBulletAvatar.setFitHeight(data.getBulletHeight() * shrink);
			normalBulletAvatar.setPreserveRatio(true);
			panel.getChildren().addAll(normalBulletAvatar);
		}
		}else {
			
			Text gameOver = new Text(-0.1 * shrink * defaultMainHeight + .30 * shrink * defaultMainWidth,
					-0.2 * shrink * 1 + shrink * 200, "Vous avez perdu avec un score de: " + data.getScore());
			Text scoreSmallMonster = new Text(-0.1 * shrink * defaultMainHeight + .30 * shrink * defaultMainWidth,
					-0.1 * shrink * 1 + shrink * 240, "Vous avez abattu: " + data.getCountSmallMonsterKilled()+" petit vaisseau");
			Text scoreMediumMonster = new Text(-0.1 * shrink * defaultMainHeight + .30 * shrink * defaultMainWidth,
					-0.1 * shrink * 1 + shrink * 280, "Vous avez abattu: " + data.getCountMediumMonsterKilled()+" moyen vaisseau");
			gameOver.setFont(new Font(.05 * shrink * defaultMainHeight));
			gameOver.setStyle(topText);
			gameOver.setFill(Color.WHITE);
			scoreSmallMonster.setFont(new Font(.05 * shrink * defaultMainHeight));
			scoreSmallMonster.setStyle(topText);
			scoreSmallMonster.setFill(Color.WHITE);
			scoreMediumMonster.setFont(new Font(.05 * shrink * defaultMainHeight));
			scoreMediumMonster.setStyle(topText);
			scoreMediumMonster.setFill(Color.WHITE);
			panel.getChildren().addAll(gameOver,scoreSmallMonster,scoreMediumMonster);
		}
		return panel;
	}

	@Override
	public void setMainWindowWidth(double width) {
		xShrink = width / defaultMainWidth;
	}

	@Override
	public void setMainWindowHeight(double height) {
		yShrink = height / defaultMainHeight;
	}
}