package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.model.Payment;
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

import java.math.BigDecimal;
import java.sql.*;
import java.util.Objects;

public class HandlePaymentsController {

    @FXML private TextField bookingIdField;
    @FXML private TextField amountField;
    @FXML private TextField lateFeeField;
    @FXML private ComboBox<String> methodBox;

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> paymentIdCol;
    @FXML private TableColumn<Payment, Integer> bookingIdCol;
    @FXML private TableColumn<Payment, BigDecimal> amountCol;
    @FXML private TableColumn<Payment, BigDecimal> lateFeeCol;
    @FXML private TableColumn<Payment, String> methodCol;

    private ObservableList<Payment> payments;

    @FXML
    public void initialize() {
        methodBox.getItems().addAll("Cash", "Credit Card", "Online");

        paymentIdCol.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        lateFeeCol.setCellValueFactory(new PropertyValueFactory<>("lateFee"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        loadPayments();

        paymentTable.setOnMouseClicked(event -> {
            Payment selected = paymentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                bookingIdField.setText(String.valueOf(selected.getBookingId()));
                amountField.setText(selected.getAmount().toString());
                lateFeeField.setText(selected.getLateFee().toString());
                methodBox.setValue(selected.getPaymentMethod());
            }
        });
    }

    private void loadPayments() {
        payments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM payments";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getTimestamp("payment_date"),
                        rs.getBigDecimal("amount"), // FIXED
                        rs.getString("payment_method"),
                        rs.getBigDecimal("late_fee")
                ));
            }

            paymentTable.setItems(payments);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load payments.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePayment() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            BigDecimal amount = new BigDecimal(amountField.getText());
            BigDecimal lateFee = lateFeeField.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lateFeeField.getText());
            String method = methodBox.getValue();

            if (method == null) {
                showAlert(Alert.AlertType.WARNING, "Select a payment method.");
                return;
            }

            String sql = "INSERT INTO payments (booking_id, amount, late_fee, payment_method, payment_date) VALUES (?, ?, ?, ?, NOW())"; // FIXED

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, bookingId);
                stmt.setBigDecimal(2, amount);
                stmt.setBigDecimal(3, lateFee);
                stmt.setString(4, method);

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Payment recorded.");
                clearFields();
                loadPayments();
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    @FXML
    private void editPayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Select a payment to edit.");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            BigDecimal lateFee = lateFeeField.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lateFeeField.getText());
            String method = methodBox.getValue();

            String sql = "UPDATE payments SET amount = ?, late_fee = ?, payment_method = ? WHERE payment_id = ?"; // FIXED

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setBigDecimal(1, amount);
                stmt.setBigDecimal(2, lateFee);
                stmt.setString(3, method);
                stmt.setInt(4, selected.getPaymentId());

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Payment updated.");
                clearFields();
                loadPayments();
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error updating payment: " + e.getMessage());
        }
    }

    @FXML
    private void deletePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Select a payment to delete.");
            return;
        }

        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selected.getPaymentId());
            stmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Payment deleted.");
            clearFields();
            loadPayments();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error deleting payment: " + e.getMessage());
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) bookingIdField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Payment");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearFields() {
        bookingIdField.clear();
        amountField.clear();
        lateFeeField.clear();
        methodBox.getSelectionModel().clearSelection();
        paymentTable.getSelectionModel().clearSelection();
    }
}