package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;


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

}
