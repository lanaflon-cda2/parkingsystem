package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService extends ReducCalaculator {


    public FareCalculatorService(ClientDAO clientDAO) {
        super(clientDAO);
    }

    @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:FinalParameters", "checkstyle:AvoidNestedBlocks"})
    public void calculateFare(Ticket ticket) throws CloneNotSupportedException {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }
        long inDay2 = ticket.getInTime().getTime();
        long outDay2 = ticket.getOutTime().getTime();

        String vehicule = ticket.getVehicleRegNumber();

        long durationInMinutes = Math.abs(outDay2 - inDay2) / 1000 / 60; //  Delta des 2 dates en Minutes
        //TODO: Some tests are failing here. Need to check if this logic is correct : Ok

        long durationInDay = durationInMinutes / 60;
        long rest = (durationInMinutes - (durationInDay * 60)); // reste de la division pour obtenir les Minutes restantes
        double rest2 = (double) rest / 60;
        double durationConcat = durationInDay + rest2;

        double suppressMin = minTimer(durationConcat);

        double durationTotal = reducCalc(suppressMin, vehicule);


        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(durationTotal * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(durationTotal * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }

    }
}
