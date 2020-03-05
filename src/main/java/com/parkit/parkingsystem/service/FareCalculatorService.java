package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FareCalculatorService {

    ClientDAO clientDAO = new ClientDAO();

    @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:FinalParameters", "checkstyle:AvoidNestedBlocks"})
    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }
        long inDay2 = ticket.getInTime().getTime();
        long outDay2 = ticket.getOutTime().getTime();
        long durationInMinutes = Math.abs(outDay2 - inDay2) / 1000 / 60; //  Delta des 2 dates en Minutes

        //TODO: Some tests are failing here. Need to check if this logic is correct : Ok
        boolean recurentClient = false;
        long durationInDay = durationInMinutes / 60;
        long rest = (durationInMinutes - (durationInDay * 60)); // reste de la division pour obtenir les Minutes restantes
        double rest2 = (double) rest / 60;
        double durationTotal = durationInDay + rest2;

        durationTotal -= 0.50; // Gratuité des 30 premieres minutes
        double durationTotal2 = Math.round(durationTotal * (1.0 / 0.01)) / (1.0 / 0.01);
        if (durationTotal2 < 0) {
            durationTotal2 = 0.00;
        }

        //ClientDAO clientDAO = new ClientDAO();
        recurentClient = clientDAO.getClient("123pl91");
        if (recurentClient) {
            durationTotal2 *= Fare.RECURENT_CLIENT_REDUC;
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(durationTotal2 * Fare.CAR_RATE_PER_HOUR);

                break;
            }
            case BIKE: {
                ticket.setPrice(durationTotal2 * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }

    }
}
