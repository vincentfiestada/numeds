package edu.up.numeds;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * A simple JavaFX-based GUI app which allows a user to the use the LagrangeInterpolator
 * and DecomposableMatrix classes for Lagrange Interpolation and LU Decomposition, respectively
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.1          
 */

public class NumericalMethods extends Application
{
	Stage mainWindow;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
		mainWindow = primaryStage;
		mainWindow.setMinWidth(200);
		mainWindow.setMinHeight(200);
        primaryStage.setTitle("CS 131 LE 3 Numerical Methods Implementation");

		// >>> Create Application Menu <<<
		Label menuLabel = new Label("Choose a numerical method");
		// Numerical methods to choose from:
        Button btnLagrange = new Button("Lagrange Interpolation");
		btnLagrange.setOnAction(e -> new LagrangeWindow().display());
		Button btnLU = new Button("Crout's Method");
		btnLU.setOnAction(e -> new LUWindow().display());

		// Menu layout - vertical button menu 
		VBox menuLayout = new VBox(20);
		menuLayout.getChildren().addAll(menuLabel, btnLagrange, btnLU);
        
        primaryStage.setScene(new Scene(menuLayout, 600, 350));
        primaryStage.show();
    }
}