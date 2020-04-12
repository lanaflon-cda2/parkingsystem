package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @Mock
    private static ClientDAO clientDAO;


    @BeforeEach
    private void setUpPerTest() {
        MockitoAnnotations.initMocks(this);
        fareCalculatorService = new FareCalculatorService(clientDAO);
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR/2);
    }

    @Test
    public void calculateFareBike() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR/2);
    }

    @Test
    public void calculateFareUnkownType() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act et Assert
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act et Assert
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals((0.25 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals( (0.25 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals( (23.5 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithLessThan30MinParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  15 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals( 0, ticket.getPrice());
    }

    @Test
    public void calculateFareBikeWithLessThan30MinParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  15 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWith30MinParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals( (0 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareBikeWith30MinParkingTime() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareBikeRecurentTwoHoursParkingTime() {

        // Arrange
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

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals(1.42, ticket.getPrice(), 0.001);

    }

    @Test
    public void calculateFareCarRecurentTwoHoursParkingTime() {

        // Arrange
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

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals(2.13, ticket.getPrice(), 0.001 );

    }

    @Test
    public void calculateFareCarZeroMinute() {

        // Arrange
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis());
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);

        // Act
        fareCalculatorService.calculateFare(ticket);

        // Assert
        assertEquals(0, ticket.getPrice());
    }

}
