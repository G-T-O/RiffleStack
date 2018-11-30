package alpha;

import tools.HardCodedParameters;
import tools.User;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import data.Data;
import engine.Engine;
import userInterface.Viewer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainStage extends Application {
	// ---VARIABLES---//
	private static DataService data;
	private static EngineService engine;
	private static ViewerService viewer;
	private static AnimationTimer timer;
	private static Stage stage;
	private static Thread t;

	public MainStage(Stage stage, int choice, String playerName) {
		this.stage = stage;
		data = new Data();
		engine = new Engine();
		viewer = new Viewer();
		((Engine) engine).bindDataService(data);
		((Viewer) viewer).bindReadService(data);
		data.init();
		engine.init();
		viewer.init();
		data.setHeroesChoice(choice);
		data.setPlayerName(playerName);
	}

	public void StartMainStage() {
		final Scene scene = new Scene(((Viewer) viewer).getPanel());
		scene.setFill(Color.CORNFLOWERBLUE);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT)
					engine.setHeroesCommand(User.COMMAND.LEFT);
				if (event.getCode() == KeyCode.RIGHT)
					engine.setHeroesCommand(User.COMMAND.RIGHT);
				if (event.getCode() == KeyCode.UP)
					engine.setHeroesCommand(User.COMMAND.UP);
				if (event.getCode() == KeyCode.DOWN)
					engine.setHeroesCommand(User.COMMAND.DOWN);
				if (event.getCode() == KeyCode.SPACE)
					engine.setHeroesCommand(User.COMMAND.SPACE);
				event.consume();
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT)
					engine.releaseHeroesCommand(User.COMMAND.LEFT);
				if (event.getCode() == KeyCode.RIGHT)
					engine.releaseHeroesCommand(User.COMMAND.RIGHT);
				if (event.getCode() == KeyCode.UP)
					engine.releaseHeroesCommand(User.COMMAND.UP);
				if (event.getCode() == KeyCode.DOWN)
					engine.releaseHeroesCommand(User.COMMAND.DOWN);
				event.consume();
			}
		});
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				viewer.setMainWindowWidth(newSceneWidth.doubleValue());
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				viewer.setMainWindowHeight(newSceneHeight.doubleValue());
			}
		});

		stage.setScene(scene);
		stage.setWidth(HardCodedParameters.defaultWidth);
		stage.setHeight(HardCodedParameters.defaultHeight);
		stage.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.start();
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.stop();
			}
		});
		stage.show();

		timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				scene.setRoot(((Viewer) viewer).getPanel());
				switch (data.getSoundEffect()) {
				case MonsterDestroyed:
					t = new Thread() {
						public void run() {
							new MediaPlayer(new Media(getHostServices().getDocumentBase() + "src/sound/waterdrip.mp3"))
									.play();
						}
					};
					t.start();
					break;
				case HeroesGotHit:
					t = new Thread() {
						public void run() {
							new MediaPlayer(new Media(getHostServices().getDocumentBase() + "src/sound/waterdrip.mp3"))
									.play();
						}
					};
					t.start();
					break;
				case HeroesShoot:
					t = new Thread() {
						public void run() {
							new MediaPlayer(
									new Media(getHostServices().getDocumentBase() + "src/sound/shoot/laser1.mp3"))
											.play();
						}
					};
					t.start();
				default:
					break;
				}
			}
		};
		timer.start();
	}

	@Override
	public void start(Stage arg0) throws Exception {
	}
}
