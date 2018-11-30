package userInterface;

import tools.HardCodedParameters;
import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.control.TextField;
import alpha.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class StartViewer implements ViewerService, RequireReadService {
	private static final int spriteSlowDownRate = HardCodedParameters.spriteSlowDownRate;
	private static final double defaultMainWidth = HardCodedParameters.defaultWidth,
			defaultMainHeight = HardCodedParameters.defaultHeight;
	private ReadService data;
	private Group panel;
	private double xShrink, yShrink, shrink, xModifier, yModifier, radius;
	private Image spaceBG, imgStart, imgHeroes;
	private ImageView spaceBGView, imgStartView, heroesAvatar;
	private TextField txtName;
	private int choice = 1, maxHeroes = 3;

	public StartViewer() {
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

		// Yucky hard-conding
		spaceBG = new Image("file:src/images/SBG.jpg");

		spaceBGView = new ImageView(spaceBG);
		spaceBGView.setTranslateX(0);
		spaceBGView.setTranslateY(0);
		spaceBGView.setPreserveRatio(true);
		spaceBGView.toBack();

		radius = .5 * Math.min(shrink * data.getSmallMonsterWidth(), shrink * data.getSmallMonsterHeight());
		imgStart = new Image("file:src/images/StartImg.png");
		imgStartView = new ImageView(imgStart);
		imgStartView.setTranslateX(shrink * 100 + shrink * xModifier - radius);
		imgStartView.setTranslateY(0);
		imgStartView.setFitHeight(100);
		imgStartView.setPreserveRatio(true);

		imgHeroes = new Image("file:src/images/heroes/SS" + choice + "_1.png");
		heroesAvatar = new ImageView(imgHeroes);
		heroesAvatar.setTranslateX(335);
		heroesAvatar.setTranslateY(200);
		heroesAvatar.setFitHeight(60);
		heroesAvatar.setPreserveRatio(true);

		String btnArrowStyle = "-fx-background-color: #090a0c," + "linear-gradient(#107BED, #191d22),"
				+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
				+ "-fx-background-radius: 5,4,3,5;" + "-fx-background-insets: 0,1,2,0;" + "-fx-text-fill: white;"
				+ " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				+ "-fx-text-fill: linear-gradient(white, #d0d0d0);" + "-fx-font-size: 24px;"
				+ "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );"
				+ "-fx-padding: 10 20 10 20;";
		Button btnLeft = new Button();
		btnLeft.setShape(new Polygon(new double[] { 5.0, -5.0, 5.0, 5.0, 0.0, 0.0 }));
		btnLeft.setStyle(btnArrowStyle);
		btnLeft.setLayoutX(shrink * 250 + shrink * xModifier - radius);
		btnLeft.setLayoutY(shrink * 200 + shrink * yModifier - radius);

		Button btnRight = new Button();
		btnRight.setShape(new Polygon(new double[] { 0.0, 0.0, 2.0, 3.0, 0.0, 6.0 }));
		btnRight.setStyle(btnArrowStyle);
		btnRight.setLayoutX(shrink * 480 + shrink * xModifier - radius);
		btnRight.setLayoutY(shrink * 200 + shrink * yModifier - radius);

		String txtFieldName = "-fx-background-color: #090a0c," + "linear-gradient(#107BED, #191d22),"
				+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
				+ "-fx-background-radius: 5,4,3,5;" + "-fx-background-insets: 0,1,2,0;" + "-fx-text-fill: white;"
				+ " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				+ "-fx-text-fill: linear-gradient(white, #d0d0d0);" + "-fx-font-size: 24px;"
				+ "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );" + "-fx-pref-width: 280;"
				+ "-fx-padding: 10 20 10 20;";
		txtName = new TextField();
		txtName.setPromptText("USERNAME");
		txtName.setStyle(txtFieldName);
		txtName.setTranslateX(250);
		txtName.setTranslateY(280);

		String darkBlueStartBtn = "-fx-background-color: #090a0c," + "linear-gradient(#107BED, #191d22),"
				+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
				+ "-fx-background-radius: 5,4,3,5;" + "-fx-background-insets: 0,1,2,0;" + "-fx-text-fill: white;"
				+ " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				+ "-fx-text-fill: linear-gradient(white, #d0d0d0);" + "-fx-font-size: 24px;" + "-fx-pref-width: 280;"
				+ "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );"
				+ "-fx-padding: 10 20 10 20;";
		Button btn = new Button("START THE GAME");
		btn.setStyle(darkBlueStartBtn);
		btn.toFront();
		btn.setLayoutX(250);
		btn.setLayoutY(400);

		String QuitBtn = "-fx-background-color:#090a0c," + "linear-gradient(#38424b 0%, #1f2429 20%, #107bed 100%),"
				+ "linear-gradient(#fff, #e6f1fd),"
				+ "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
				+ "-fx-background-radius: 5,4,3,5;" + "-fx-background-insets: 0,1,2,0;"
				+ " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
				+ "-fx-text-fill: #000;" + "-fx-font-size: 24px;" + "-fx-pref-width: 280;"
				+ "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 );"
				+ "-fx-padding: 10 20 10 20;";
		Button btnQ = new Button("QUIT");
		btnQ.setStyle(QuitBtn);
		btnQ.toFront();
		btnQ.setLayoutX(250);
		btnQ.setLayoutY(470);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtName.getText();
				Main.runMainStage(choice,txtName.getText());
			}
		});

		btnLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (choice == 1) {
					choice = maxHeroes;
				} else {
					choice--;
				}
				Main.refresh();
			}
		});

		btnRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (choice == 3) {
					choice = 1;
				} else {
					choice++;
				}
				Main.refresh();
			}
		});
		panel = new Group();
		panel.getChildren().addAll(spaceBGView, btn, btnQ, imgStartView, txtName, heroesAvatar, btnRight, btnLeft);

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