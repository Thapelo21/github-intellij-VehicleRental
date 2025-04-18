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
import java.time.LocalDateTime;

public class ManageCustomerController {

    @FXML private TextField nameField, contactField, emailField, licenseField;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> idCol;
    @FXML private TableColumn<Customer, String> nameCol, contactCol, emailCol, licenseCol;
    @FXML private TableColumn<Customer, LocalDateTime> dateCol;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        licenseCol.setCellValueFactory(new PropertyValueFactory<>("drivingLicenseNumber"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        loadCustomers();

        customerTable.setOnMouseClicked(e -> {
            Customer selected = customerTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nameField.setText(selected.getFullName());
                contactField.setText(selected.getContactNumber());
                emailField.setText(selected.getEmail());
                licenseField.setText(selected.getDrivingLicenseNumber());
            }
        });
    }

    private void loadCustomers() {
        customerList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) { // Corrected method
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("full_name"),
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("driving_license_number"),
                        rs.getTimestamp("registration_date").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerTable.setItems(customerList);
    }

    @FXML
    private void handleAdd() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO customers (full_name, contact_number, email, driving_license_number) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, contactField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, licenseField.getText());
            stmt.executeUpdate();
            loadCustomers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE customers SET full_name=?, contact_number=?, email=?, driving_license_number=? WHERE customer_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, contactField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, licenseField.getText());
            stmt.setInt(5, selected.getCustomerId());
            stmt.executeUpdate();
            loadCustomers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM customers WHERE customer_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selected.getCustomerId());
            stmt.executeUpdate();
            loadCustomers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearForm() {
        nameField.clear();
        contactField.clear();
        emailField.clear();
        licenseField.clear();
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/vehiclerentalsystem/admin_dashboard.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}