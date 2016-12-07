package edu.up.numeds;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
/**
 * JavaFX window which contains GUI for the Crout's method implementation
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.4          
 */

public class LUWindow
{
	public static void display()
	{
		Stage window = new Stage();
		
		// Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Crout's Method");
		window.setMinWidth(200);
		window.setMinHeight(200);

		// >>> Create LU Decomposition UI <<<
		Label luLabel = new Label("LU Decomposition with Crout's Method");
		// Lagrange layout 
		StackPane root = new StackPane();
		root.getChildren().add(luLabel);
		window.setScene(new Scene(root, 300, 350));
		window.showAndWait(); // Block the menu window
	}
}