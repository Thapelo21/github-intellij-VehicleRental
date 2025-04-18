package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.dao.PaymentDAO;
import com.example.VehicleRentalSystem.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class EditPaymentController {

    private Payment currentPayment;

    @FXML private TextField amountField;
    @FXML private TextField lateFeeField;
    @FXML private ComboBox<String> methodBox;

    @FXML
    public void initialize() {
        methodBox.getItems().addAll("Cash", "Credit Card", "Online");
    }

    public void setPaymentData(Payment payment) {
        this.currentPayment = payment;
        amountField.setText(payment.getAmount().toPlainString());
        lateFeeField.setText(payment.getLateFee().toPlainString());
        methodBox.setValue(payment.getPaymentMethod());
    }

    @FXML
    private void handleUpdate() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            BigDecimal lateFee = lateFeeField.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(lateFeeField.getText());
            String method = methodBox.getValue();

            if (method == null || method.isEmpty()) {
                showAlert("Error", "Please select a payment method.");
                return;
            }

            currentPayment.setAmount(amount);
            currentPayment.setLateFee(lateFee);
            currentPayment.setPaymentMethod(method);

            boolean success = PaymentDAO.updatePayment(currentPayment);
            if (success) {
                showAlert("Success", "Payment updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Update failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number input.");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
