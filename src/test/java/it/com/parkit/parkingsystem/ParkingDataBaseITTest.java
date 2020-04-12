package it.com.parkit.parkingsystem;


import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import it.com.parkit.parkingsystem.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseITTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        dataBasePrepareService.clearDataBaseEntries();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }


    @Test
    public void testParkingACarAndExit() throws InterruptedException {

        // Arrange
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        // Act
        parkingService.processIncomingVehicle();
        Thread.sleep(2000);
        parkingService.processExitingVehicle();
        ticket = ticketDAO.getTicket("ABCDEF");

        // Assert
        assertEquals(0, ticket.getPrice());
        assertNotNull(ticket.getInTime());
        assertNotNull(ticket.getOutTime());
        assertNotNull(ticket.getParkingSpot());
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
        //TODO: check that the fare generated and out time are populated correctly in the database : OK
    }

    @Test
    public void testParkingACar() {

        // Arrange
        Date inTime = new Date();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        // Act
        parkingService.processIncomingVehicle();
        ticket = ticketDAO.getTicket("ABCDEF");

        // Assert
        assertEquals(0, ticket.getPrice());
        assertNotNull(ticket.getInTime());
        assertNotNull(ticket.getParkingSpot());
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability :OK
    }

}