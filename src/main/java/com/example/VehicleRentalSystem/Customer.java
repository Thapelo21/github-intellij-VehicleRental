package com.example.VehicleRentalSystem;

import java.time.LocalDateTime;

public class Customer {
    private int customerId;
    private String fullName;
    private String contactNumber;
    private String email;
    private String drivingLicenseNumber;
    private LocalDateTime registrationDate;

    public Customer(int customerId, String fullName, String contactNumber, String email, String drivingLicenseNumber, LocalDateTime registrationDate) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.registrationDate = registrationDate;
    }

    public int getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }
    public String getDrivingLicenseNumber() { return drivingLicenseNumber; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setDrivingLicenseNumber(String drivingLicenseNumber) { this.drivingLicenseNumber = drivingLicenseNumber; }
}
