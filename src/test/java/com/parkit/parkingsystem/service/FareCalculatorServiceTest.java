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

public class FareCalculatorServiceTest {

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
    public void calculateFareCar() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, CloneNotSupportedException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double test = ticket.getPrice();
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR/2);
    }

    @Test
    //@Disabled
    public void calculateFareBike() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        double test = ticket.getPrice();
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR/2);
    }

    @Test
    //@Disabled
    public void calculateFareUnkownType(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    //@Disabled
    public void calculateFareBikeWithFutureInTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    //@Disabled
    public void calculateFareBikeWithLessThanOneHourParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.25 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    //@Disabled
    public void calculateFareCarWithLessThanOneHourParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.25 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    //@Disabled
    public void calculateFareCarWithMoreThanADayParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (23.5 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    //@Disabled
    public void calculateFareCarWithLessThan30MinParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  15 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    //@Disabled
    public void calculateFareBikeWithLessThan30MinParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  15 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    //@Disabled
    public void calculateFareCarWith30MinParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    @Test
    //@Disabled
    public void calculateFareBikeWith30MinParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    //@Disabled
    public void calculateFareBikeRecurentTwoHoursParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  120 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setVehicleRegNumber("ABCDEF");
        String vehicule = ticket.getVehicleRegNumber();

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        when(clientDAO.getClient(vehicule)).thenReturn(true);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((1.5 * Fare.BIKE_RATE_PER_HOUR)*95/100, ticket.getPrice(), 0.001 );
    }

    @Test
    //@Disabled
    public void calculateFareCarRecurentTwoHoursParkingTime() throws CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  120 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setVehicleRegNumber("ABCDEF");
        String vehicule = ticket.getVehicleRegNumber();

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        when(clientDAO.getClient(vehicule)).thenReturn(true);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((1.5 * Fare.CAR_RATE_PER_HOUR)*95/100, ticket.getPrice(), 0.001 );
    }
}
