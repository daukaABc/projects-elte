package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.BinaryOperation;
import model.Calculator;
import model.CalculatorException;

public class CalculatorController implements Initializable {

	private Calculator calc;
	
	public CalculatorController(Calculator calc){
		this.calc = calc;
	}
	
	@FXML
	private TextField resultField;

	@FXML
	private GridPane numbersPane;
	
	@FXML
	private Button mulButton;
	
	@FXML
	private Button divButton;
	
	@FXML
	private Button plusButton;
	
	@FXML
	private Button minusButton;
	
	@FXML
	private Button equalsButton;
	
	private void setResult(int res) {
		resultField.setText(String.valueOf(res));
	}
	
	private void setResult(String res) {
		resultField.setText(res);
	}
	
	private EventHandler<ActionEvent> createHandleOpPress(BinaryOperation op) {
		return (ActionEvent ev) -> {
			setNextOperand();
			calc.setOp(op);
			setResult(0);
		};
	}
	
	private void initializeButtons() {
		mulButton.setOnAction(createHandleOpPress(BinaryOperation.MUL));
		divButton.setOnAction(createHandleOpPress(BinaryOperation.DIV));
		plusButton.setOnAction(createHandleOpPress(BinaryOperation.PLUS));
		minusButton.setOnAction(createHandleOpPress(BinaryOperation.MINUS));
		equalsButton.setOnAction(ev -> {
			try {
				setNextOperand();
				int res = calc.calculate();
				setResult(res);
			} catch (CalculatorException e) {
				e.printStackTrace();
			}
		});
	}

	private void setNextOperand() {
		int num = Integer.parseInt(resultField.getText());
		calc.setNextOperand(num);
	}
	
	private void stepInput(int x) {
		String text = resultField.getText();
		if (text.equals("0")) {
			setResult(x);
		} else {
			setResult(text + String.valueOf(x));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeButtons();
		
		resultField.setEditable(false);
		resultField.setText("0");
		resultField.setFocusTraversable(false);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				int val = 9 - 3 * j - (2 - i);
				Button child = new Button(Integer.toString(9 - 3 * j - (2 - i)));
				child.setOnAction(ev -> stepInput(val));
				numbersPane.add(child, i, j);
			}
		}
	}
}
