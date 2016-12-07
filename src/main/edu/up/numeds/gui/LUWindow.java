package edu.up.numeds;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.*;
import edu.up.numeds.LagrangeInterpolator;
import edu.up.numeds.OrderedPair;
import javafx.collections.*;
import javafx.util.converter.NumberStringConverter;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * JavaFX window which contains GUI for the Crout's method implementation
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.4          
 */

public class LUWindow
{
	public LUWindow()
	{
		_matrix = FXCollections.observableArrayList();
		_L = FXCollections.observableArrayList();
		_U = FXCollections.observableArrayList();
		_size = 2;
		_tableA = new TableView<>();
		_tableL = new TableView<>();
		_tableU = new TableView<>();
	}

	public LUWindow(int size)
	{
		_matrix = FXCollections.observableArrayList();
		_L = FXCollections.observableArrayList();
		_U = FXCollections.observableArrayList();
		_size = size;
		_tableA = new TableView<>();
		_tableL = new TableView<>();
		_tableU = new TableView<>();
	}

	private ObservableList<ArrayList<Double>> _matrix;
	private ObservableList<ArrayList<Double>> _L;
	private ObservableList<ArrayList<Double>> _U;
	private int _size;
	private TableView<ArrayList<Double>> _tableA;
	private TableView<ArrayList<Double>> _tableL;
	private TableView<ArrayList<Double>> _tableU;

	public void display()
	{
		Stage window = new Stage();
		
		// Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Crout's Method");
		window.setMinWidth(400);
		window.setMinHeight(200);

		// >>> Create LU Decomposition UI <<<
		createColumns(); // Add columns to A Table
		populateRowsOfA();
		_tableA.setEditable(true);

		// Create Tabs 
		TabPane tabPane = new TabPane();
		BorderPane mainPane = new BorderPane();
		Tab tabA = new Tab(); // A Tab
		tabA.setClosable(false);
		tabA.setText("A Matrix");
		tabA.setContent(_tableA);
		tabPane.getTabs().add(tabA);
		Tab tabL = new Tab(); // L Tab
		tabL.setClosable(false);
		tabL.setText("L Component");
		tabL.setContent(_tableL);
		tabPane.getTabs().add(tabL);
		Tab tabU = new Tab(); // U Tab
		tabU.setClosable(false);
		tabU.setText("U Component");
		tabU.setContent(_tableU);
		tabPane.getTabs().add(tabU);

		// Controls 
		Button decomposeBtn = new Button("Decompose A");
		decomposeBtn.setOnAction(e -> {
			findLU();
		});
		TextField sizeField = new TextField();
		sizeField.setPromptText("Size of A");
		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(e -> {
			try
			{
				Integer k = Integer.valueOf(sizeField.getText());
				if (k < 2) return;
				_size = k;
				_L.clear();
				_U.clear();
				_tableA.getColumns().clear();
				_tableL.getColumns().clear();
				_tableU.getColumns().clear();
				createColumns();
				populateRowsOfA();
			}
			catch(NumberFormatException numErr)
			{
				System.err.println("Invalid format for size of A");
			}
		});
		Button exportBtn = new Button("Export A, L, U as CSV");
		exportBtn.setOnAction(e -> {
			// Create File Save dialog
			try 
			{
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Export as CSV");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showSaveDialog(window);
				if (file != null && (file.canWrite() || file.createNewFile()))
				{
					System.out.println("Writing CSV to " + file.getAbsoluteFile());
					FileWriter writer = new FileWriter(file.getAbsoluteFile());
					CSVWriter csv = new CSVWriter(writer);
					// Export A 
					for (ArrayList<Double> row : _matrix)
					{
						csv.row(row);
					}
					// Create border 
					csv.border(" ", _size);
					// Export L 
					for (ArrayList<Double> row : _L)
					{
						csv.row(row);
					}
					csv.border(" ", _size);
					// Export U
					for (ArrayList<Double> row : _U)
					{
						csv.row(row);
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

		HBox controlBar = new HBox(20);
		controlBar.setPadding(new Insets(10, 10, 10, 10));
		controlBar.getChildren().addAll(decomposeBtn, sizeField, resetBtn, exportBtn);

		mainPane.setCenter(tabPane);
		mainPane.setBottom(controlBar);

		// Lagrange layout 
		VBox root = new VBox(20);
		root.getChildren().addAll(mainPane);
		window.setScene(new Scene(root, 650, 650));
		window.showAndWait(); // Block the menu window
	}

	/**
	 * Create the columns for the A, L and U tables
	 */
	private void createColumns()
	{
		for (int i = 0; i < _size; i++)
		{
			TableColumn<ArrayList<Double>, String> tc = new TableColumn<>(String.valueOf(i));
			TableColumn<ArrayList<Double>, String> ltc = new TableColumn<>(String.valueOf(i));
			TableColumn<ArrayList<Double>, String> utc = new TableColumn<>(String.valueOf(i));
			final int colNo = i;
			// Value is from the corresponding element of the matrix 
			Callback<CellDataFeatures<ArrayList<Double>, String>, ObservableValue<String>> cvFactory = new Callback<CellDataFeatures<ArrayList<Double>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<ArrayList<Double>, String> p) {
					return new SimpleObjectProperty<>(p.getValue().get(colNo).toString());
                }
            };
			tc.setCellValueFactory(cvFactory);
			ltc.setCellValueFactory(cvFactory);
			utc.setCellValueFactory(cvFactory);
			// if edited, edit matrix as well 
			tc.setCellFactory(TextFieldTableCell.forTableColumn());
			tc.setOnEditCommit(t -> {
				try 
				{
					Double dVal = Double.valueOf(t.getNewValue()); 
					if (dVal == null || dVal.isNaN())
					{
						((ArrayList<Double>) t.getTableView().getItems().get(t.getTablePosition().getRow())).set(t.getTablePosition().getColumn(), 0.0);
					}
					else 
					{
						((ArrayList<Double>) t.getTableView().getItems().get(t.getTablePosition().getRow())).set(t.getTablePosition().getColumn(), dVal);
					}
				}
				catch(NumberFormatException numErr)
				{
					System.err.println("Invalid number format");
					((ArrayList<Double>) t.getTableView().getItems().get(t.getTablePosition().getRow())).set(t.getTablePosition().getColumn(), 0.0);
				}
			});
			// Set preferred widths 
			tc.setPrefWidth(50);
			ltc.setPrefWidth(50);
			utc.setPrefWidth(50);
			// Disable sorting (we don't want the user accidentally rearranging the matrix)
			tc.setSortable(false);
			ltc.setSortable(false);
			utc.setSortable(false); 
			_tableA.getColumns().add(tc);
			_tableL.getColumns().add(ltc);
			_tableU.getColumns().add(utc);
		}
	}

	/**
	 * Populate cells of the A table with zeroes
	 */
	private void populateRowsOfA()
	{
		_tableA.getItems().clear();
		for (int i = 0; i < _size; i++)
		{
			ArrayList<Double> a = new ArrayList();
			for (int j = 0; j < _size; j++)
			{
				a.add(0.0);
			}
			_matrix.add(a);
		}
		_tableA.setItems(_matrix);
	}
	
	/**
	 * Decompose the matrix into L and U and populate the relevant tables 
	 */
	private void findLU()
	{
		try 
		{
			// Create a DecomposableMatrix from A 
			DecomposableMatrix A = new DecomposableMatrix(_size);
			int i, j;
			i = 0;
			for (ArrayList<Double> row : _matrix)
			{
				j = 0;
				for (Double cell : row)
				{
					A.set(i, j, cell);
					j++;
				}
				i++;
			}
			// Run Crout's method 
			A.decomposeLU();
			// Populate L table 
			Matrix l = A.getL();
			_L.clear();
			for (i = 0; i < _size; i++)
			{
				ArrayList<Double> a = new ArrayList();
				for (j = 0; j < _size; j++)
				{
					a.add(l.get(i, j));
				}
				_L.add(a);
			}
			_tableL.setItems(_L);
			// Populate U table 
			Matrix u = A.getU();
			_U.clear();
			for (i = 0; i < _size; i++)
			{
				ArrayList<Double> a = new ArrayList();
				for (j = 0; j < _size; j++)
				{
					a.add(u.get(i, j));
				}
				_U.add(a);
			}
			_tableU.setItems(_U);
		}
		catch (NotDecomposedException decompErr) 
		{
			System.err.println(decompErr);
		}
	}
}