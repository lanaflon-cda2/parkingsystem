package it.com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ClientDAO;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import it.com.parkit.parkingsystem.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import junit.framework.Assert;
import org.apache.logging.log4j.core.config.Order;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static java.lang.invoke.MethodHandles.catchException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseBikeITTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static TicketDAO ticketDAO2;
    private static TicketDAO ticketDAO3;

    private static DataBasePrepareService dataBasePrepareService;
    private Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO2 = new TicketDAO();
        ticketDAO3 = new TicketDAO();

        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        dataBasePrepareService.clearDataBaseEntries();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("123pl91");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    @Disabled
    public void testParkingACarAndExit() throws InterruptedException {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Thread.sleep(2000);
        parkingService.processExitingVehicle();

        ticket = ticketDAO.getTicket("123pl91");
        assertEquals(0, ticket.getPrice());
        assertNotNull(ticket.getInTime());
        assertNotNull(ticket.getOutTime());
        assertNotNull(ticket.getParkingSpot());
        assertEquals("123pl91", ticket.getVehicleRegNumber());
    }



//    @Test(expected = Exception.class)
//    public void testParkingABike() throws Exception {
//        Date inTime = new Date();
//        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
//        parkingService.processIncomingVehicle();
//        ticket = ticketDAO.getTicket("123pl91");
//        parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
//        parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE);
//
//
//        assertThrows(Exception.class, parkingService::getNextParkingNumberIfAvailable);
//        assertEquals(0, ticket.getPrice());
//        assertNotNull(ticket.getInTime());
//        assertNotNull(ticket.getParkingSpot());
//        assertEquals("123pl91", ticket.getVehicleRegNumber());

//    }

}

