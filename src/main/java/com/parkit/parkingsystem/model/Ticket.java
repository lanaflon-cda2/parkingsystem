package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        Date dateInTime = inTime;
        return dateInTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = new Date (inTime.getTime());
    }

    public Date getOutTime() {
        Date dateOutTime = outTime;
        return dateOutTime;
    }

    public void setOutTime(Date outTime) {
        if (outTime == null){
            this.outTime = null;
        } else
            this.outTime = new Date (outTime.getTime());
    }
}
