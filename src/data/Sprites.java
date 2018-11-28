package data;
import specifications.ReadService;
import specifications.RequireReadService;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Sprites {
	private List<Image> images = new ArrayList<>();
	private List<Image> deathImages = new ArrayList<>();
	private int counter = -1;
	private int size = 7;
	private int heroesChoice;
	
	private void load(String filePath) {
		for (int i = 1; i < size; i++) {
			images.add(new Image(filePath + i + ".png"));
		}
	}
	public void setImage(int choice) {
		heroesChoice=choice;
	}
	
	public Image getImage() {
		if (images.isEmpty()) {
			this.load("file:src/images/heroes/SS"+heroesChoice+"_");
			System.out.println("Is loaded");
		}
		counter = ++counter % images.size();
		return images.get(counter);
	}

	public Image getDeathImage() {
		if (deathImages.isEmpty()) {
			this.load("file:src/images/heroes/DEATH_");
		}
		counter = ++counter % images.size();
		return images.get(counter);
	}

}