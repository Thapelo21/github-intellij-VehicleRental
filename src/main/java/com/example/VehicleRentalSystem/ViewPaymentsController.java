package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.PaymentDAO;
import com.example.VehicleRentalSystem.model.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewPaymentsController {

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> idCol;
    @FXML private TableColumn<Payment, Integer> bookingIdCol;
    @FXML private TableColumn<Payment, Double> amountCol;
    @FXML private TableColumn<Payment, Double> lateFeeCol;
    @FXML private TableColumn<Payment, String> methodCol;
    @FXML private TableColumn<Payment, String> dateCol;

    private ObservableList<Payment> paymentList;
    private String userRole = "Admin";

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        lateFeeCol.setCellValueFactory(new PropertyValueFactory<>("lateFee"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        loadPayments();
    }

    private void loadPayments() {
        List<Payment> list = PaymentDAO.getAllPayments();
        paymentList = FXCollections.observableArrayList(list);
        paymentTable.setItems(paymentList);
    }

    @FXML
    private void updatePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/edit_payment.fxml"));
                Parent root = loader.load();

                EditPaymentController controller = loader.getController();
                controller.setPaymentData(selected);

                Stage stage = new Stage();
                stage.setTitle("Edit Payment");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Failed to open edit payment window.");
            }
        } else {
            showAlert("Select a payment to update.");
        }
    }

    @FXML
    private void deletePayment() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean success = PaymentDAO.deletePayment(selected.getPaymentId());
            if (success) {
                showAlert("Payment deleted.");
                loadPayments();
            } else {
                showAlert("Failed to delete payment.");
            }
        } else {
            showAlert("Select a payment to delete.");
        }
    }

    @FXML
    private void generateInvoice() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/VehicleRentalSystem/invoice.fxml"));
                Parent root = loader.load();

                InvoiceController controller = loader.getController();
                controller.setPayment(selected);

                Stage stage = new Stage();
                stage.setTitle("Invoice - Payment #" + selected.getPaymentId());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Failed to generate invoice.");
            }
        } else {
            showAlert("Select a payment to generate invoice.");
        }
    }

    @FXML
    private void goBack() {
        try {
            String targetDashboard = userRole.equalsIgnoreCase("Admin")
                    ? "/com/example/VehicleRentalSystem/admin_dashboard.fxml"
                    : "/com/example/VehicleRentalSystem/employee_dashboard.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetDashboard));
            Parent root = loader.load();
            Stage stage = (Stage) paymentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(userRole + " Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Could not navigate back to dashboard.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }
}
