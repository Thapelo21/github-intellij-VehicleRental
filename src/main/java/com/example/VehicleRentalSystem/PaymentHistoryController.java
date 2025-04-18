package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.PaymentDAO;
import com.example.VehicleRentalSystem.model.Payment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class PaymentHistoryController {

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> idCol;
    @FXML private TableColumn<Payment, Integer> bookingCol;
    @FXML private TableColumn<Payment, String> dateCol;
    @FXML private TableColumn<Payment, String> methodCol;
    @FXML private TableColumn<Payment, Double> amountCol;
    @FXML private TableColumn<Payment, Double> lateFeeCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        bookingCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        lateFeeCol.setCellValueFactory(new PropertyValueFactory<>("lateFee"));

        List<Payment> payments = PaymentDAO.getAllPayments();
        paymentTable.getItems().setAll(payments);
    }

    @FXML
    private void exportToPDF() {
        List<Payment> payments = PaymentDAO.getAllPayments();
        String filePath = "PaymentHistoryReport.pdf";
        PaymentPDFExporter.export(payments, filePath);
        showAlert(Alert.AlertType.INFORMATION, "PDF Exported", "Saved to: " + filePath);
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/VehicleRentalSystem/admin_dashboard.fxml"));
            Stage stage = (Stage) paymentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not return to the dashboard.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
