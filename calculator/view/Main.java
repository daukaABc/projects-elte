package view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import controller.CalculatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Calculator;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try { 
			Calculator calc = new Calculator();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculatorView.fxml"));
			loader.setController(new CalculatorController(calc));
			BorderPane root = (BorderPane) loader.load(); 
			Scene scene = new Scene(root, 315, 305);
			scene.getStylesheets().add(getClass().getResource("calculator.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Calculator");
			primaryStage.setOnCloseRequest(ev -> {
				try ( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("calculator.dat"))) {
					out.writeObject(calc);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
