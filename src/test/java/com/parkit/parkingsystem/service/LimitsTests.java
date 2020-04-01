package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class LimitsTests {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;
//    private Object Null;

    @Mock
    private static ClientDAO clientDAO;


//    @BeforeAll
//    private static void setUp() {
//
//    }

    @BeforeEach
    private void setUpPerTest() {
        MockitoAnnotations.initMocks(this);
        fareCalculatorService = new FareCalculatorService(clientDAO);
        ticket = new Ticket();
    }


    @Test
    //@Disabled
    public void calculateFareCarZeroMinute() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, CloneNotSupportedException {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis());
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(0, ticket.getPrice());
    }

}