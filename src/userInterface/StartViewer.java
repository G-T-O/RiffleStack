package userInterface;

import tools.HardCodedParameters;
import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import alpha.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartViewer implements ViewerService, RequireReadService {
	private static final int spriteSlowDownRate = HardCodedParameters.spriteSlowDownRate;
	private static final double defaultMainWidth = HardCodedParameters.defaultWidth,
			defaultMainHeight = HardCodedParameters.defaultHeight;
	private ReadService data;
	private Button btn, btnLeft, btnRight;
	private Group panel;
	private Text greets;
	private double xShrink, yShrink, shrink, xModifier, yModifier, radius;
	private Image imgStart, imgHeroes, btnLeftArrow, btnRightArrow;
	private ImageView imgStartView, heroesAvatar;
	private Label labName;
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
		greets = new Text(-0.12 * shrink * defaultMainHeight + .5 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 30, "Bienvenue");
		greets.setFont(new Font(.05 * shrink * defaultMainHeight));

		radius = .5 * Math.min(shrink * data.getSmallMonsterWidth(), shrink * data.getSmallMonsterHeight());
		imgStart = new Image("file:src/images/StartImg.png");
		imgStartView = new ImageView(imgStart);
		imgStartView.setTranslateX(shrink * 100 + shrink * xModifier - radius);
		imgStartView.setTranslateY(shrink * 100 + shrink * yModifier - radius);
		imgStartView.setFitHeight(data.getSmallMonsterHeight() * shrink);
		imgStartView.setPreserveRatio(true);

		imgHeroes = new Image("file:src/images/heroes/SS" + choice + "_1.png");
		heroesAvatar = new ImageView(imgHeroes);
		heroesAvatar.setTranslateX(shrink * 348 + shrink * xModifier - radius);
		heroesAvatar.setTranslateY(shrink * 275 + shrink * yModifier - radius);
		heroesAvatar.setFitHeight(data.getHeroesHeight() * shrink);
		heroesAvatar.setPreserveRatio(true);

		btnLeftArrow = new Image("file:src/images/leftArrow.png");
		btnLeft = new Button(null, new ImageView(btnLeftArrow));
		btnLeft.setTranslateX(shrink * 280 + shrink * xModifier - radius);
		btnLeft.setTranslateY(shrink * 275 + shrink * yModifier - radius);

		btnRightArrow = new Image("file:src/images/rightArrow.png");
		btnRight = new Button(null, new ImageView(btnRightArrow));
		btnRight.setTranslateX(shrink * 450 + shrink * xModifier - radius);
		btnRight.setTranslateY(shrink * 275 + shrink * yModifier - radius);

		labName = new Label("Name");
		labName.setTranslateX(shrink * 270 + shrink * xModifier - radius);
		labName.setTranslateY(shrink * 375 + shrink * yModifier - radius);

		txtName = new TextField();
		txtName.setPromptText("Enter your name.");
		txtName.setPrefColumnCount(10);
		txtName.setTranslateX(shrink * 330 + shrink * xModifier - radius);
		txtName.setTranslateY(shrink * 375 + shrink * yModifier - radius);

		btn = new Button("Start");
		btn.setLayoutX(380);
		btn.setLayoutY(450);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtName.getText();
				Main.runMainStage(choice);
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
		panel.getChildren().addAll(greets, btn, imgStartView, labName, txtName, heroesAvatar, btnRight, btnLeft);
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