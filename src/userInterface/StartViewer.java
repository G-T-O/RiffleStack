package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import alpha.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartViewer implements ViewerService, RequireReadService {
	private static final int spriteSlowDownRate = HardCodedParameters.spriteSlowDownRate;
	private static final double defaultMainWidth = HardCodedParameters.defaultWidth,
			defaultMainHeight = HardCodedParameters.defaultHeight;
	private ReadService data;
	private Button btn;
	private Group panel;
	private Text greets;
	private double xShrink, yShrink, shrink, xModifier, yModifier, radius;
	private Image imgStart;
	private ImageView imgStartView;
	private Label labName;
	private TextField txtName;
	//private HBox hb = new HBox();

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
		greets = new Text(-0.1 * shrink * defaultMainHeight + .5 * shrink * defaultMainWidth,
				-0.1 * shrink * 1 + shrink * 40, "Bienvenue");
		greets.setFont(new Font(.05 * shrink * defaultMainHeight));

		radius = .5 * Math.min(shrink * data.getSmallMonsterWidth(), shrink * data.getSmallMonsterHeight());
		imgStart = new Image("file:src/images/StartImg.png");
		imgStartView = new ImageView(imgStart);
		imgStartView.setTranslateX(shrink * 100 + shrink * xModifier - radius);
		imgStartView.setTranslateY(shrink * 100 + shrink * yModifier - radius);
		imgStartView.setFitHeight(data.getSmallMonsterHeight() * shrink);
		imgStartView.setPreserveRatio(true);

		labName = new Label("Name");
		labName.setTranslateX(shrink * 300 + shrink * xModifier - radius);
		labName.setTranslateY(shrink * 300 + shrink * yModifier - radius);
		txtName = new TextField();
		txtName.setPromptText("Enter your name.");
		txtName.setPrefColumnCount(10);
		txtName.setTranslateX(shrink * 350 + shrink * xModifier - radius);
		txtName.setTranslateY(shrink * 300 + shrink * yModifier - radius);
		btn = new Button("Start");
		btn.setLayoutX(400);
		btn.setLayoutY(400);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				txtName.getText();
				Main.runMainStage();
			}
		});
		panel = new Group();
		panel.getChildren().addAll(greets, btn,imgStartView,labName,txtName);
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
