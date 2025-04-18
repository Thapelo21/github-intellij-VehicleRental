package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.model.Payment;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class InvoiceController {

    @FXML private Label paymentIdLabel;
    @FXML private Label bookingIdLabel;
    @FXML private Label paymentDateLabel;
    @FXML private Label amountLabel;
    @FXML private Label lateFeeLabel;
    @FXML private Label totalLabel;
    @FXML private Label methodLabel;
    @FXML private VBox root;

    public void setPayment(Payment payment) {
        paymentIdLabel.setText(String.valueOf(payment.getPaymentId()));
        bookingIdLabel.setText(String.valueOf(payment.getBookingId()));
        paymentDateLabel.setText(payment.getPaymentDate().toString());
        amountLabel.setText("LSL " + payment.getAmount().toString());
        lateFeeLabel.setText("LSL " + payment.getLateFee().toString());

        BigDecimal total = payment.getAmount().add(payment.getLateFee());
        totalLabel.setText("LSL " + total.toString());

        methodLabel.setText(payment.getPaymentMethod());
    }

    @FXML
    private void printInvoice() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(root.getScene().getWindow())) {
            boolean success = job.printPage(root);
            if (success) {
                job.endJob();
            }
        }
    }
}
