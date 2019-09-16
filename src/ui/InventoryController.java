package ui;

import java.io.File;
import java.util.Comparator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import model.HNode;
import model.InventoryManager;
import model.OpenAddressingHashTable;
import model.SetOfBlocks;

public class InventoryController {

	@FXML
	private TextField quickAccessBarIdentifier;

	@FXML
	private GridPane quickAccessBar;

	@FXML
	private GridPane inventoryGridPane;

	@FXML
	private ToggleGroup actionToggleGroup;

	@FXML
	private ChoiceBox<String> typeOfBlockChoiceBox;

	@FXML
	private ImageView blockPreview;

	@FXML
	private TextField amountTextField;

	private InventoryManager im;

	@FXML
	public void initialize() {
		im = new InventoryManager();

		File images = new File("images");
		for (File image: images.listFiles()) {
			String fileName = image.getName();
			if(!fileName.equals("icon.jpeg")) {
				typeOfBlockChoiceBox.getItems().add(fileName.replace(".png", "").replace(".gif", ""));
			}
		}

		typeOfBlockChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(!typeOfBlockChoiceBox.getSelectionModel().getSelectedItem().equals("Chest")) {
					blockPreview.setImage(new Image(new File("images" + File.separator + typeOfBlockChoiceBox.getSelectionModel().getSelectedItem()+".png").toURI().toString()));
				} else {
					blockPreview.setImage(new Image(new File("images" + File.separator + typeOfBlockChoiceBox.getSelectionModel().getSelectedItem()+".gif").toURI().toString()));
				}
			}

		});

		typeOfBlockChoiceBox.getSelectionModel().selectFirst();

		actionToggleGroup.getToggles().get(0).setUserData("Collect");
		actionToggleGroup.getToggles().get(1).setUserData("Consume");
		actionToggleGroup.selectToggle(actionToggleGroup.getToggles().get(0));

		//TODO seccion de pruebas
		inventoryGridPane.getChildren().sort(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if(o1 instanceof ImageView && o2 instanceof Label) {
					return -1;
				} else if(o1 instanceof Label && o2 instanceof ImageView) {
					return 1;
				}
				return 0;
			}

		});
	}

	@FXML
	public void collectOrConsumeButtonPressed(ActionEvent event) {
		switch((String)actionToggleGroup.getSelectedToggle().getUserData()) {
		case "Collect":
			try {
				im.collect(typeOfBlockChoiceBox.getSelectionModel().getSelectedItem(), Integer.parseInt(amountTextField.getText()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "Consume":
			try {
				im.consume(Integer.parseInt(amountTextField.getText()));
			} catch (Exception e) {
				//e.printStackTrace();
			}
			break;
		}
		refreshImagesAndLabels();
	}

	@FXML
	public void decrease(ActionEvent event) {
		int currentAmount = Integer.parseInt(amountTextField.getText());
		if(currentAmount > 0) {
			currentAmount--;
		}
		amountTextField.setText(currentAmount+"");
	}

	@FXML
	public void increase(ActionEvent event) {
		int currentAmount = Integer.parseInt(amountTextField.getText());
		currentAmount++;
		amountTextField.setText(currentAmount+"");
	}

	@FXML
	public void nextQuickAccessBarButtonPressed(ActionEvent event) {
		try {
			im.nextQuickAccessBar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		refreshImagesAndLabels();
	}

	@FXML
	public void verifyInput(KeyEvent event) {
		char input =event.getCharacter().charAt(0);
		if(!((input >= '0' && input <= '9') || input == '\b')) {
			event.consume();
		}
	}

	public void refreshImagesAndLabels() {
		ObservableList<Node> quickAccessBarNodes = quickAccessBar.getChildren();
		for(int i = 0; i < 9; i++) { //Clear
			((ImageView)quickAccessBarNodes.get(i)).setImage(null);
			((Label)quickAccessBarNodes.get(i+9)).setText("0");
		}
		if(!im.getQuickAccessBars().isEmpty()) {
			String[] cqab = im.getQuickAccessBars().front().toString().split("\n");
			for (int i = cqab.length - 1, j = 8; i > -1 && j > -1; i--, j--) {
				String[] node = cqab[i].split(",");
				ImageView image = (ImageView)quickAccessBar.getChildren().get(j);
				node[0] = node[0].substring(0, node[0].length() - 5);
				if(node[0].equals("Chest")) {
					image.setImage(new Image(new File("images" + File.separator + node[0] + ".gif").toURI().toString()));
				} else {
					image.setImage(new Image(new File("images" + File.separator + node[0] + ".png").toURI().toString()));
				}

				Label amountLabel = (Label)quickAccessBar.getChildren().get(j+9);
				amountLabel.setText(node[1]);
			}
		}

		ObservableList<Node> gInventoryNodes = inventoryGridPane.getChildren();

		int nrows = 3;
		int ncols = 9;
		for(int i = 0; i < nrows; i++) { //Clear
			for (int j = 0; j < ncols; j++) {
				((ImageView)gInventoryNodes.get(i * ncols + j)).setImage(null);
				((Label)gInventoryNodes.get((i + 3)* ncols + j)).setText("0");
			}
		}

		OpenAddressingHashTable<String, SetOfBlocks> inv = im.getInventory();
		
		int count = 0;
		for(int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				if(inv.getItems()[count] != null && !inv.getDELETED()[count]) {
					String typeOfBlocks = inv.getItems()[count].getKey();
					typeOfBlocks = typeOfBlocks.substring(0, typeOfBlocks.length() - 5);
					if(typeOfBlocks.equals("Chest")) {
						((ImageView)gInventoryNodes.get(i * ncols + j)).setImage(new Image(new File("images" + File.separator + typeOfBlocks + ".gif").toURI().toString()));
					} else {
						((ImageView)gInventoryNodes.get(i * ncols + j)).setImage(new Image(new File("images" + File.separator + typeOfBlocks + ".png").toURI().toString()));
					}
					((Label)gInventoryNodes.get((i + 3)* ncols + j)).setText(inv.getItems()[count].getValue().getBlocks()+"");
				}
				count++;
			}
		}
	}
}

