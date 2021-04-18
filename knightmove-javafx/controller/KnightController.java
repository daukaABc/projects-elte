package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class KnightController implements Initializable {
	
	@FXML
	private HBox sizeHBox;
	
	@FXML
	private Label stepsLabel;
	
	@FXML
	private GridPane buttonsPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillWithSizeButton();
		createGameBoard();
		
	}
	
	private void fillWithSizeButton() {
		for (int size = 6; size <= 10; size+=2){
			Button sizeButton = new Button(size + " x " + size);
			sizeHBox.getChildren().add(sizeButton);
		}
	}

	public void createGameBoard() {
		for (int i = 0; i < 6; ++i){
			for (int j = 0; j < 6; ++j){
				Button btn = new Button();
				btn.getStyleClass().add("tableButton");
				buttonsPane.add(btn, i, j);
			}
		}
	}
	
}
