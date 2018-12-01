package alpha;

import tools.HardCodedParameters;
import specifications.DataService;
import data.Data;
import userInterface.StartViewer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

	public class Main extends Application {
	// ---VARIABLES---//
	private static DataService data;
	private static StartViewer startViewer;
	private static MainStage mainStage;
	private static Stage stg;
	// ---EXECUTABLE---/

	public static void main(String[] args) {
		data = new Data();
		startViewer = new StartViewer();
		startViewer.init();
		((StartViewer) startViewer).bindReadService(data);
		startViewer.init();
		launch(args);
	}

	public static void runMainStage(int choice, String playerName) {

		stg.close();
		mainStage = new MainStage(stg, choice,playerName);
		mainStage.StartMainStage();
	}

	@Override
	public void start(Stage stage) {
		new MediaPlayer(new Media(getHostServices().getDocumentBase() + "src/sound/song.mp3")).play();
		final Scene scene = new Scene(((StartViewer) startViewer).getPanel());
		scene.setFill(Color.CORNFLOWERBLUE);
		stage.setScene(scene);
		stage.setWidth(HardCodedParameters.defaultWidth);
		stage.setHeight(HardCodedParameters.defaultHeight);
		stage.show();
		stg = stage;
	}

	public static void refresh() {
		final Scene scene = new Scene(((StartViewer) startViewer).getPanel());
		scene.setFill(Color.CORNFLOWERBLUE);
		stg.setScene(scene);
		stg.setWidth(HardCodedParameters.defaultWidth);
		stg.setHeight(HardCodedParameters.defaultHeight);
		stg.show();
	}
}
