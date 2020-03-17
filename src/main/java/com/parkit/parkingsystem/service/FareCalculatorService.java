package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.lang.reflect.InvocationTargetException;

public class FareCalculatorService extends ReducCalaculator {


    public FareCalculatorService(ClientDAO clientDAO) {
        super(clientDAO);
    }

    public void calculateFare(Ticket ticket) throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

        double price = setPriceVehicule(durationTotal, ticket.getParkingSpot().getParkingType().toString());
        ticket.setPrice(price);

    }
}
