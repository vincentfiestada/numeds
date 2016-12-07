package edu.up.numeds;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import edu.up.numeds.LagrangeInterpolator;
import edu.up.numeds.OrderedPair;
import javafx.collections.*;
import javafx.util.converter.NumberStringConverter;
import javafx.util.Callback;
import javafx.geometry.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX window which contains GUI for the Lagrange Interpolation implementation
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.4          
 */

public class LagrangeWindow
{
	public LagrangeWindow()
	{
		_knownPoints = FXCollections.observableArrayList();
		_xInt = FXCollections.observableArrayList();
		_lagrange = new LagrangeInterpolator(_knownPoints);
	}

	private ObservableList<OrderedPair<Double>> _knownPoints;
	private ObservableList<OrderedPair<Double>> _xInt; // X-values to interpolate at 
	private LagrangeInterpolator _lagrange; // Interpolator

	public void display()
	{
		Stage window = new Stage();
		
		// Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Lagrange Interpolation");
		window.setMinWidth(400);
		window.setMinHeight(200);

		// >>> Create Lagrange Interpolation UI <<<
		Label knownPointsLabel = new Label("Known Points");

		// X column
		TableColumn<OrderedPair<Double>, Double> xColumn = new TableColumn<OrderedPair<Double>, Double>("X Value");
		xColumn.setMinWidth(100);
		xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		// Y column
		TableColumn<OrderedPair<Double>, Double> yColumn = new TableColumn<OrderedPair<Double>, Double>("Y Value");
		yColumn.setMinWidth(100);
		yColumn.setCellValueFactory(new PropertyValueFactory<OrderedPair<Double>, Double>("y"));

		TableView<OrderedPair<Double>> knownPointsTable = new TableView<OrderedPair<Double>>();
		knownPointsTable.setItems(_knownPoints);
		knownPointsTable.getColumns().addAll(xColumn, yColumn);

		// New Known Point Input Controls 
		TextField newKnownXInput = new TextField();
		newKnownXInput.setPromptText("New X");
		TextField newKnownYInput = new TextField();
		newKnownYInput.setPromptText("New Y");
		Button addNewKnownPointBtn = new Button("Add");
		addNewKnownPointBtn.setOnAction(e -> {
			// Add new known point 
			try 
			{
				Double x = Double.valueOf(newKnownXInput.getText());
				Double y = Double.valueOf(newKnownYInput.getText());
				// Add to table 
				_knownPoints.add(new OrderedPair<Double>(x, y));
				// Add to interpolator 
				_lagrange.addPoint(x, y);
				// Re-interpolate 
				for (int i = 0; i < _xInt.size(); i++) 
				{
					Double xint = _xInt.get(i).getX();
					Double yint = _lagrange.interpolate(xint);
					_xInt.set(i, new OrderedPair<Double>(xint, yint));
				}	
				// Clear input fields 
				newKnownXInput.clear();
				newKnownYInput.clear();
			}
			catch (InterpolatorNotReadyException notReadyErr) 
			{
				System.err.println("Interpolator not ready");
			} 
			catch(NumberFormatException numErr) 
			{
				System.err.println("Invalid Number format");
			}
		});

		Button deleteKnownPointBtn = new Button("Delete");
		deleteKnownPointBtn.setOnAction(e -> {
			try
			{
				// Delete currently selected known point/s 
				ObservableList<OrderedPair<Double>> selectedPoints = knownPointsTable.getSelectionModel().getSelectedItems();
				for (OrderedPair<Double> i : selectedPoints)
				{
					knownPointsTable.getItems().remove(i); // Remove from table 
					_lagrange.removePoint(i); // Remove from interpolator
				}
				// Re-interpolate 
				for (int i = 0; i < _xInt.size(); i++) 
				{
					Double xint = _xInt.get(i).getX();
					Double yint = _lagrange.interpolate(xint);
					_xInt.set(i, new OrderedPair<Double>(xint, yint));
				}	
			}
			catch (InterpolatorNotReadyException notReadyErr) 
			{
				System.err.println("Interpolator not ready");
			} 
		});

		HBox newKnownPointBox = new HBox(10);
		newKnownPointBox.setPadding(new Insets(10, 10, 10, 10));
		newKnownPointBox.getChildren().addAll(deleteKnownPointBtn, newKnownXInput, newKnownYInput, addNewKnownPointBtn);

		VBox knownPointsPanel = new VBox(10);
		knownPointsPanel.getChildren().addAll(knownPointsLabel, knownPointsTable, newKnownPointBox);

		// Points to interpolate 
		Label xIntLabel = new Label("Interpolate At...");

		// X column
		TableColumn<OrderedPair<Double>, Double> xIntColumn = new TableColumn<>("X");
		xIntColumn.setMinWidth(100);
		xIntColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		// Y Column 
		TableColumn<OrderedPair<Double>, Double> yIntColumn = new TableColumn<>("Interpolated Y");
		yIntColumn.setMinWidth(100);
		yIntColumn.setCellValueFactory(new PropertyValueFactory<>("y"));

		TableView<OrderedPair<Double>> xIntTable = new TableView<>();
		xIntTable.setItems(_xInt);
		xIntTable.getColumns().addAll(xIntColumn, yIntColumn);

		// New Known Point Input Controls 
		TextField newXInt = new TextField();
		newXInt.setPromptText("New X Int");
		Button addXIntBtn = new Button("Add");
		addXIntBtn.setOnAction(e -> {
			// Add new point to interpolate at
			try 
			{
				Double x = Double.valueOf(newXInt.getText());
				Double y = _lagrange.interpolate(x); // Interpolate y-value
				_xInt.add(new OrderedPair<Double>(x, y)); // Add to table 
				// Clear input fields 
				newXInt.clear();
			}
			catch (InterpolatorNotReadyException notReadyErr) 
			{
				System.err.println("Interpolator not ready");
			} 
			catch (NumberFormatException numErr) 
			{
				System.err.println("Invalid Number format");
			}
		});

		Button deleteXIntBtn = new Button("Delete");
		deleteXIntBtn.setOnAction(e -> {
			// Delete currently selected point/s 
			ObservableList<OrderedPair<Double>> selectedPoints = xIntTable.getSelectionModel().getSelectedItems();
			for (OrderedPair<Double> i : selectedPoints)
			{
				xIntTable.getItems().remove(i);
			}
		});

		Button exportBtn = new Button("Export as CSV");
		exportBtn.setOnAction(e -> {
			// Create File Save dialog
			try 
			{
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Export as CSV");
				File file = fileChooser.showSaveDialog(window);
				if (file != null && (file.canWrite() || file.createNewFile()))
				{
					System.out.println("Writing CSV to " + file.getAbsoluteFile());
					FileWriter writer = new FileWriter(file.getAbsoluteFile());
					ArrayList<String> headers = new ArrayList();
					headers.add("X");
					headers.add("Y");
					headers.add("Interpolated?");
					CSVWriter csv = new CSVWriter(writer, headers);
					// Export known points
					for (OrderedPair<Double> point : _knownPoints)
					{
						csv.rowOrderedPair(point.getX(), point.getY(), "No");
					}
					// Export interpolated points
					for (OrderedPair<Double> point : _xInt)
					{
						csv.rowOrderedPair(point.getX(), point.getY(), "Yes");
					}
					// Flush and close writer 
					csv.finish();
				}
			}
			catch (SecurityException | IOException err)
			{
				System.err.println(err);
			}
		});

		HBox newXIntBox = new HBox(10);
		newXIntBox.setPadding(new Insets(10, 10, 10, 10));
		newXIntBox.getChildren().addAll(deleteXIntBtn, newXInt, addXIntBtn, exportBtn);

		VBox xIntPanel = new VBox(10);
		xIntPanel.getChildren().addAll(xIntLabel, xIntTable, newXIntBox);

		// Lagrange layout 
		HBox lagrangeRoot = new HBox(20);
		lagrangeRoot.getChildren().addAll(knownPointsPanel, xIntPanel);

		window.setScene(new Scene(lagrangeRoot, 900, 350));
		window.showAndWait(); // Block the menu window
	}
}