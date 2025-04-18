package com.example.VehicleRentalSystem.dao;

import com.example.VehicleRentalSystem.model.Booking;
import com.example.VehicleRentalSystem.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BookingDAO {

    // Insert new booking
    public static boolean insertBookings(Booking booking) {
        String sql = "INSERT INTO bookings (customer_id, vehicle_id, rental_start, rental_end, booking_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getVehicleId());
            stmt.setDate(3, Date.valueOf(booking.getRentalStart()));
            stmt.setDate(4, Date.valueOf(booking.getRentalEnd()));
            stmt.setString(5, booking.getBookingStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all bookings
    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("vehicle_id"),
                        rs.getDate("rental_start").toLocalDate(),
                        rs.getDate("rental_end").toLocalDate(),
                        rs.getString("booking_status")
                );
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    // Update existing booking
    public static boolean updateBookings(Booking booking) {
        String sql = "UPDATE bookings SET customer_id = ?, vehicle_id = ?, rental_start = ?, rental_end = ?, booking_status = ? WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getVehicleId());
            stmt.setDate(3, Date.valueOf(booking.getRentalStart()));
            stmt.setDate(4, Date.valueOf(booking.getRentalEnd()));
            stmt.setString(5, booking.getBookingStatus());
            stmt.setInt(6, booking.getBookingId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancel booking (soft delete by changing status)
    public static boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET booking_status = 'Cancelled' WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get customer rental history (past and present bookings)
    public static List<Booking> getRentalHistory() {
        List<Booking> history = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY rental_start DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("vehicle_id"),
                        rs.getDate("rental_start").toLocalDate(),
                        rs.getDate("rental_end").toLocalDate(),
                        rs.getString("booking_status")
                );
                history.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }

    // Get top 5 booked vehicles for chart
    public static Map<String, Integer> getTopBookedVehicles() {
        Map<String, Integer> topVehicles = new LinkedHashMap<>();
        String sql = """
            SELECT v.model AS model, COUNT(b.vehicle_id) AS bookings
            FROM bookings b
            JOIN vehicles v ON b.vehicle_id = v.vehicle_id
            GROUP BY v.model
            ORDER BY bookings DESC
            LIMIT 5
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                topVehicles.put(rs.getString("model"), rs.getInt("bookings"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topVehicles;
    }
}
