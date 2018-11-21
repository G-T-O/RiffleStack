
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

public class Main extends Application{
  //---HARD-CODED-PARAMETERS---//
  private static String fileName = HardCodedParameters.defaultParamFileName;

  //---VARIABLES---//
  private static DataService data;
  private static StartViewer startViewer;
  private static MainStage mainStage; 
  private static Stage stg;
 
  //---EXECUTABLE---/
  
  public static void main(String[] args) {
	data = new Data();
	startViewer = new StartViewer();
	startViewer.init();
    ((StartViewer)startViewer).bindReadService(data);
    startViewer.init();    
    launch(args);
  }
  
  public static void runMainStage() {
	  
	  stg.close();
	  
	  mainStage = new MainStage(stg);
	  mainStage.StartMainStage();
  }

  @Override public void start(Stage stage) {
	 
	  new MediaPlayer(new Media(getHostServices().getDocumentBase()+"src/sound/song.mp3")).play();

    final Scene scene = new Scene(((StartViewer)startViewer).getPanel());
    scene.setFill(Color.CORNFLOWERBLUE);
    stage.setScene(scene);
    stage.setWidth(HardCodedParameters.defaultWidth);
    stage.setHeight(HardCodedParameters.defaultHeight);
    stage.show();
    stg=stage;
	 // mainStage = new MainStage(stage);
	 // mainStage.StartMainStage();
  }

  //---ARGUMENTS---//
  private static void readArguments(String[] args){
    if (args.length>0 && args[0].charAt(0)!='-'){
      System.err.println("Syntax error: use option -h for help.");
      return;
    }
    for (int i=0;i<args.length;i++){
      if (args[i].charAt(0)=='-'){
	if (args[i+1].charAt(0)=='-'){
	  System.err.println("Option "+args[i]+" expects an argument but received none.");
	  return;
	}
	switch (args[i]){
	  case "-inFile":
	    fileName=args[i+1];
	    break;
	  case "-h":
	    System.out.println("Options:");
	    System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"+HardCodedParameters.defaultParamFileName+".");
	    break;
	  default:
	    System.err.println("Unknown option "+args[i]+".");
	    return;
	}
	i++;
      }
    }
  }
}
