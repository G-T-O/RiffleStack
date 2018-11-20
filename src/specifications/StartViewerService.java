package specifications;

import javafx.scene.Parent;

public interface StartViewerService {
	  public void init();
	  public Parent getPanel();
	  public void setMainWindowWidth(double w);
	  public void setMainWindowHeight(double h);
}
