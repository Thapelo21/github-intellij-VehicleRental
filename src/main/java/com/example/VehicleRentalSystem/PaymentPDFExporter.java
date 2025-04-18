package com.example.VehicleRentalSystem;

import com.example.VehicleRentalSystem.model.Payment;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class PaymentPDFExporter {

    public static void export(List<Payment> payments, String filename) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Payment History Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 2f, 3f, 3f, 2f, 2f});
            table.setSpacingBefore(10);

            // Add header
            addTableHeader(table, "Payment ID", "Booking ID", "Date", "Method", "Amount", "Late Fee");

            for (Payment p : payments) {
                table.addCell(String.valueOf(p.getPaymentId()));
                table.addCell(String.valueOf(p.getBookingId()));
                table.addCell(String.valueOf(p.getPaymentDate()));
                table.addCell(p.getPaymentMethod());
                table.addCell("LSL " + p.getAmount());
                table.addCell("LSL " + p.getLateFee());
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private static void addTableHeader(PdfPTable table, String... headers) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
}
