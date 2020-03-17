package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.Bike;
import com.parkit.parkingsystem.model.Car;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReducCalaculator {
    private final ClientDAO clientDAO;

    public ReducCalaculator(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }


    public double reducCalc(double duration, String vehicule) {

        if (clientDAO.getClient(vehicule)) {
            duration *= Fare.RECURENT_CLIENT_REDUC;
        }
        return duration;
    }


    public double minTimer(double durationConcat) {

        durationConcat -= 0.50; // Gratuité des 30 premieres minutes
        double durationTotal = Math.round(durationConcat * (1.0 / 0.01)) / (1.0 / 0.01);
        if (durationTotal < 0) {
            durationTotal = 0.00;
        }
        return durationTotal;
    }


    public double setPriceVehicule(double durationTotal, String vehicule) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

/*        String methodName = "setPriceV";
        String chaine = vehicule.toLowerCase();
        char[] char_table = chaine.toCharArray();
        char_table[0]=Character.toUpperCase(char_table[0]);
        chaine = new String(char_table);*/

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
