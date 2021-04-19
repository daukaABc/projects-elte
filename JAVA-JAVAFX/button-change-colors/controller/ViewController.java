package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.OnClick;

public class ViewController implements Initializable {

	@FXML
	private GridPane buttonsPane;
	
	private OnClick btn = new OnClick();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnClick();
	}


	private void btnClick() {
		Button b = new Button();
		b.setOnAction(click(b));
		buttonsPane.getChildren().add(b);
	}

	private EventHandler<ActionEvent> click(Button b) {
		return event -> {
			if (((btn.getClicks() % 5) == 0 && btn.getClicks() != 0)) {
				b.setText(Integer.toString(btn.getClicks()));
				b.setId("onclick");
				btn.onPress();
			} else {
				b.setText(Integer.toString(btn.getClicks()));
				b.setId("currently");
				btn.onPress();
			}
		};
	}
}
