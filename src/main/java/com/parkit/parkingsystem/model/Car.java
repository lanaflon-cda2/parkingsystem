package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.Interfaces.ISetPriceV;
import com.parkit.parkingsystem.constants.Fare;

public class Car implements ISetPriceV {

    @Override
    public double setPriceV(double price) {

        price *= Fare.CAR_RATE_PER_HOUR;
        return price;
    }

}
