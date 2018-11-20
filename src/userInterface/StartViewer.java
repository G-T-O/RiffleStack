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

public class StartViewer  implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;


  private double xShrink,yShrink,shrink;

  public StartViewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    xShrink=1;
    yShrink=1;
  }

  @Override
  public Parent getPanel(){
    shrink=Math.min(xShrink,yShrink);
    //Yucky hard-conding


    Text greets = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
    						-0.1*shrink*1+shrink*40,
                           "Bienvenue");
    greets.setFont(new Font(.05*shrink*defaultMainHeight));

    Button btn = new Button("Sign in");
    btn.setLayoutX(300);
    btn.setLayoutY(200);
    btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
           Main.runMainStage();
        }
    });
    Group panel = new Group();
    panel.getChildren().addAll(greets,btn);


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


