package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Coordinate;
import model.gomokuTable;
import model.SleazyGomokuTable;
import model.gomokuTable;

public class GomokuController implements Initializable {

	@FXML 
	private GridPane buttonsPane;
	
	@FXML
	private Label playerLabel;
	
	@FXML
	private Label stepsLabel;
	
	private final SleazyGomokuTable gameTable = new gomokuTable();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Game();
		add();
		updateView();
	
	}

	private void updateView() {
		
	}

	private void add() {
		
	}

	private void Game() {
		buttonsPane.getChildren().clear();
		for (int i = 0; i < 10; ++i){
			for (int j = 0; j < 10; ++j){
				Button b = new Button();
				b.getStyleClass().add("buttons");
				b.setOnAction(step(b, i, j));
				buttonsPane.add(b, i, j);
			}
		}
	}

	private EventHandler<ActionEvent> step(Button b, int i, int j) {
	
		
		return null;
	}

	public SleazyGomokuTable getGameTable() {
		return gameTable;
	}

}
