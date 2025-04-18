package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.VehicleDAO;
import com.example.VehicleRentalSystem.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.PrintWriter;
import java.util.List;

public class AvailableVehiclesReportController {

    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, Integer> idCol;
    @FXML private TableColumn<Vehicle, String> brandCol;
    @FXML private TableColumn<Vehicle, String> modelCol;
    @FXML private TableColumn<Vehicle, String> categoryCol;
    @FXML private TableColumn<Vehicle, String> statusCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> data.getValue().vehicleIdProperty().asObject());
        brandCol.setCellValueFactory(data -> data.getValue().brandProperty());
        modelCol.setCellValueFactory(data -> data.getValue().modelProperty());
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());
        statusCol.setCellValueFactory(data -> data.getValue().availabilityProperty());

        List<Vehicle> availableVehicles = VehicleDAO.getAvailableVehicles();
        vehicleTable.setItems(FXCollections.observableArrayList(availableVehicles));
    }


    @FXML
    private void exportToCSV() {
        try (PrintWriter writer = new PrintWriter("available_vehicles_report.csv")) {
            writer.println("Vehicle ID,Brand,Model,Category,Availability");
            for (Vehicle v : vehicleTable.getItems()) {
                writer.printf("%d,%s,%s,%s,%s%n",
                        v.getVehicleId(), v.getBrand(), v.getModel(),
                        v.getCategory(), v.getAvailability());
            }
            showAlert("Success", "Exported to CSV successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to export CSV: " + e.getMessage());
        }
    }

    @FXML
    private void exportToPDF() {
        try (PrintWriter writer = new PrintWriter("available_vehicles_report.pdf")) {
            writer.println("Available Vehicles Report\n");
            for (Vehicle v : vehicleTable.getItems()) {
                writer.printf("Vehicle ID: %d\nBrand: %s\nModel: %s\nCategory: %s\nStatus: %s\n\n",
                        v.getVehicleId(), v.getBrand(), v.getModel(), v.getCategory(), v.getAvailability());
            }
            showAlert("Success", "Exported to PDF successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to export PDF: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/view_reports.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) vehicleTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Reports & Statistics");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
