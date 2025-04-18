package com.example.VehicleRentalSystem.dao;

import com.example.VehicleRentalSystem.model.Payment;
import com.example.VehicleRentalSystem.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class PaymentDAO {

    // Add a payment
    public static boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payments (booking_id, payment_date, amount, payment_method, late_fee) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setTimestamp(2, payment.getPaymentDate());
            stmt.setBigDecimal(3, payment.getAmount());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setBigDecimal(5, payment.getLateFee());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting payment: " + e.getMessage());
            return false;
        }
    }

    // Get all payment records
    public static List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getTimestamp("payment_date"),
                        rs.getBigDecimal("amount"),
                        rs.getString("payment_method"),
                        rs.getBigDecimal("late_fee")
                );
                payments.add(payment);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching payments: " + e.getMessage());
        }

        return payments;
    }

    // Update payment
    public static boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET amount = ?, late_fee = ?, payment_method = ? WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, payment.getAmount());
            stmt.setBigDecimal(2, payment.getLateFee());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setInt(4, payment.getPaymentId());

            int rows = stmt.executeUpdate();
            System.out.println("Rows updated: " + rows);  // Debugging line
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
            return false;
        }
    }

    // Delete payment
    public static boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    // Revenue by month
    public static Map<String, Double> getMonthlyRevenueReport() {
        Map<String, Double> revenueMap = new HashMap<>();
        String sql = "SELECT DATE_FORMAT(payment_date, '%Y-%m') AS month, SUM(amount + late_fee) AS total_revenue " +
                "FROM payments GROUP BY month ORDER BY month";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                revenueMap.put(rs.getString("month"), rs.getDouble("total_revenue"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching revenue report: " + e.getMessage());
        }

        return revenueMap;
    }
}
