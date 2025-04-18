package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;

public class ManageVehiclesController {

    @FXML private TextField brandField, modelField, categoryField, priceField;
    @FXML private ComboBox<String> statusBox;
    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, Integer> idCol;
    @FXML private TableColumn<Vehicle, String> brandCol, modelCol, categoryCol, statusCol;
    @FXML private TableColumn<Vehicle, Double> priceCol;

    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        statusBox.getItems().addAll("Available", "Rented");

        idCol.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("rentalPricePerDay"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));

        loadVehicles();

        vehicleTable.setOnMouseClicked(e -> {
            Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                brandField.setText(selected.getBrand());
                modelField.setText(selected.getModel());
                categoryField.setText(selected.getCategory());
                priceField.setText(String.valueOf(selected.getRentalPricePerDay()));
                statusBox.setValue(selected.getAvailabilityStatus());
            }
        });
    }

    private void loadVehicles() {
        vehicleList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM vehicles";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                vehicleList.add(new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("category"),
                        rs.getDouble("rental_price_per_day"),
                        rs.getString("availability_status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        vehicleTable.setItems(vehicleList);
    }

    @FXML
    private void handleAdd() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO vehicles (brand, model, category, rental_price_per_day, availability_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, brandField.getText());
            stmt.setString(2, modelField.getText());
            stmt.setString(3, categoryField.getText());
            stmt.setDouble(4, Double.parseDouble(priceField.getText()));
            stmt.setString(5, statusBox.getValue());
            stmt.executeUpdate();
            loadVehicles();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE vehicles SET brand=?, model=?, category=?, rental_price_per_day=?, availability_status=? WHERE vehicle_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, brandField.getText());
            stmt.setString(2, modelField.getText());
            stmt.setString(3, categoryField.getText());
            stmt.setDouble(4, Double.parseDouble(priceField.getText()));
            stmt.setString(5, statusBox.getValue());
            stmt.setInt(6, selected.getVehicleId());
            stmt.executeUpdate();
            loadVehicles();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM vehicles WHERE vehicle_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selected.getVehicleId());
            stmt.executeUpdate();
            loadVehicles();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearForm() {
        brandField.clear();
        modelField.clear();
        categoryField.clear();
        priceField.clear();
        statusBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/vehiclerentalsystem/admin_dashboard.fxml"));
            Stage stage = (Stage) brandField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}