package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.Bike;
import com.parkit.parkingsystem.model.Car;


public class ReducService {
    private final ClientDAO clientDAO;

    public ReducService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }


    public double reducCalc(double duration, String vehicule) {

        if (clientDAO.getClient(vehicule)) {
            duration *= Fare.RECURENT_CLIENT_REDUC;
        }
        return duration;
    }


    public double setPriceVehicule(double durationTotal, String vehicule) {

        switch (vehicule) {
            case "CAR": {
                Car car = new Car();
                return car.setPriceV(durationTotal);
            }
            case "BIKE": {
                Bike bike = new Bike();
                return bike.setPriceV(durationTotal);
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }


}
