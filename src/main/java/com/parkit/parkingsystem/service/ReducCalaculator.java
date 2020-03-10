package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.dao.TicketDAO;

public class ReducCalaculator {
    private final ClientDAO clientDAO;

    public ReducCalaculator(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }


    public double reducCalc(double durationTotal2, String vehicule) {
        // this.clientDAO = clientDAO;

        if (clientDAO.getClient(vehicule)) {
            durationTotal2 *= Fare.RECURENT_CLIENT_REDUC;
        }
        return durationTotal2;
    }

    public double minTimer(double durationTotal) {

        durationTotal -= 0.50; // Gratuité des 30 premieres minutes
        double durationTotal2 = Math.round(durationTotal * (1.0 / 0.01)) / (1.0 / 0.01);
        if (durationTotal2 < 0) {
            durationTotal2 = 0.00;
        }
        return durationTotal2;
    }

}
