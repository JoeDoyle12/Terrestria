import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.controls.*;
import javafx.scene.layout.*;
import javafx.event.*;
public class Game extends Application {
	private String farmUrl = "";
	private String keepUrl = "";
	private String houseUrl = "";
	private String grassUrl = "";
	private String treeUrl = "";
	private String wallUrl = "";
	private String woodUrl = "";
	private String stoneUrl = "";
	private String foodUrl = "";
	private String goldUrl = "";
	private String seedsUrl = "";
	private String toPlace = ''; // g for grass (delete), h for house, k for keep, and f for farm, w for wall
	private int numFarms = 1;
	private int numKeeps = 1;
	private int numHouses = 1;
	private int food = 100;
	private int population = 5;
	private int numCycles = 0;
	private int wood = 10;
	private int stone = 10;
	private int gold = 10;
	private int seeds = 0;
	private int sz = 11;
	private ImageView[][] show = new ImageView[sz][sz];
	private GridPane game = new GridPane();
	private HBox buttonBox = new HBox(10);
	private VBox farm = new VBox(5);
	private VBox keep = new VBox(5);
	private VBox house = new VBox(5);
	private VBox wall = new VBox(5);
	private VBox clear = new VBox(5);
	private VBox resources = new VBox(5);
	private HBox woodBox = new HBox(5);
	private HBox stoneBox = new HBox(5);
	private HBox foodBox = new HBox(5);
	private HBox goldBox = new HBox(5);
	private HBox seedsBox = new HBox(5);
	private void update() {

	}
	private void generateTrees() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++) {
				int x = (int) (Math.random() * sz);
				while (i == 5)
					x = (int) (Math.random() * sz);
				show[i][j] = new ImageView(treeUrl);
			}

	}
	private void makeButtons() {
		ImageView img;
		keep.getChildren().addAll(new ImageView(keepUrl), new Text("Keep", Font.font("Times New Roman", 13)));
		farm.getChildren().addAll(new ImageView(farmUrl), new Text("Farm", Font.font("Times New Roman", 13)));
		house.getChildren().addAll(new ImageView(houseUrl), new Text("House", Font.font("Times New Roman", 13)));
		wall.getChildren().addAll(new ImageView(wallUrl), new Text("wall", Font.font("Times New Roman", 13)));
		clear.getChildren().addAll(new ImageView(grassUrl), new Text("Clear", Font.font("Times New Roman", 13)));
		img = new ImageView(woodUrl);
		img.setFitWidth(10);
		img.setPreserveRatio(true);
		woodBox.getChildren().addAll(img, new Text(String.valueOf(wood), Font.font("Times New Roman", 13)));
		img = new ImageView(stoneUrl);
		img.setFitWidth(10);
		img.setPreserveRatio(true);
		stoneBox.getChildren().addAll(img, new Text(String.valueOf(stone), Font.font("Times New Roman", 13)));
		img = new ImageView(foodUrl);
		img.setFitWidth(10);
		img.setPreserveRatio(true);
		foodBox.getChildren().addAll(img, new Text(String.valueOf(food), Font.font("Times New Roman", 13)));
		img = new ImageView(goldUrl);
		img.setFitWidth(10);
		img.setPreserveRatio(true);
		goldBox.getChildren().addAll(img, new Text(String.valueOf(gold), Font.font("Times New Roman", 13)));
		img = new ImageView(seedsUrl);
		img.setFitWidth(10);
		img.setPreserveRatio(true);
		seedsBox.getChildren().addAll(img, new Text(String.valueOf(seeds), Font.font("Times New Roman", 13)));
		resources.getChildren().addAll(woodBox, stoneBox, goldBox, seedsBox);
		buttonBox.getChildren().addAll(farm, keep, house, wall, clear, resources);
	}
	private void change(ImageView image) {
		String newUrl;
		switch (toPlace) {
			case 'g':
				switch (image.getUrl()) {
					case houseUrl: wood += 5; stone++; newUrl = grassUrl; break;
					case wallUrl: wood += 2; stone++; newUrl = grassUrl; break;
					case treeUrl: woods++; seeds++; newUrl = grassUrl; break;
				}
				break;
			case 'h': wood -= 5; stone--; newUrl = houseUrl; break;
			case 'k': newUrl = keepUrl; break;
			case 'w': wood -= 2; stone--; newUrl = wallUrl; break;
		}
		image = new ImageView(newUrl);
		image.setOnAction(change(image));
		update();
	}
	@Override
	public void run(Stage stage) {
		VBox pane = new VBox();
		for (int i = 0; i < show.length; i++)
			for (int j = 0; j < show[i]; j++) {
				image = new ImageView(grassUrl);
				if (i == sz / 2 && j == sz / 2)
					image = new ImageView(keepUrl);
				image.setOnAction(change(image));
				game.add(image, i, j, 1, 1);
			}
		Thread increment = new IncrementResources();
		increment.start();
		pane.setOnMouseClicked(e -> {
			if (farm.contains(e.getX(), e.getY()))
				toPlace = 'f';
			else if (keep.contains(e.getX(), e.getY()))
				toPlace = 'k';
			else if (house.contains(e.getX(), e.getY()))
				toPlace = 'h';
			else if (wall.contains(e.getX(), e.getY()))
				toPlace = 'w';
			else if (clear.contains(e.getX(), e.getY()))
				toPlace = 'g';
		});
		pane.getChildren().addAll(game, buttonBox);
	}
	private class IncrementResources extends Thread {
		@Override
		public void run() {
			while (true) {
				food += farms * 2;
				if (numCycles == 5) {
					population += population / 2 - 2;
					numCycles = -1;
				}
				if (food < population / 2)
					population--;
				food -= population / 2;
				numCycles++;
				Thread.sleep(1000);
			}
		}
	}
}
