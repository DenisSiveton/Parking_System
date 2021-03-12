package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {

    public static final String TEST_REG_NUMBER = "ABCDEF";
    private static Ticket ticket;
    private static TicketDAO ticketDAO;


    @BeforeAll
    private static void setUp() {
        ticketDAO = new TicketDAO();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
        DataBaseTestConfig dataBaseMockTestConfig = Mockito.mock(DataBaseTestConfig.class);
        ticketDAO.dataBaseConfig = dataBaseMockTestConfig;
    }
    private void initTicket(Ticket ticket) {
        Date inTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setVehicleRegNumber(TEST_REG_NUMBER);
        ticket.setParkingSpot(parkingSpot);
        ticket.setPrice(0.0);
    }
    @Test
    public void saveTicket_verifyCorrectMethodsAreCalled() throws SQLException, IOException, ClassNotFoundException {
        //ARRANGE
        initTicket(ticket);

        //ACT
        boolean result = ticketDAO.saveTicket(ticket);

        //ASSERT
        verify(ticketDAO.dataBaseConfig, times(1)).getConnection();
        verify(ticketDAO.dataBaseConfig, times(1)).closePreparedStatement(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeConnection(null);
        assertFalse(result);
    }

    @Test
    public void getTicket_verifyCorrectMethodsAreCalled() throws SQLException, IOException, ClassNotFoundException {
        //ARRANGE

        //ACT
        Ticket ticketResult = ticketDAO.getTicket(TEST_REG_NUMBER);

        //ASSERT
        verify(ticketDAO.dataBaseConfig, times(1)).getConnection();
        verify(ticketDAO.dataBaseConfig, times(1)).closePreparedStatement(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeResultSet(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeConnection(null);
        assertNull(ticketResult);

    }

    @Test
    public void updateTicket_verifyCorrectMethodsAreCalled() throws SQLException, IOException, ClassNotFoundException {
        //ARRANGE
        initTicket(ticket);

        //ACT
        boolean result = ticketDAO.updateTicket(ticket);
        //ASSERT
        verify(ticketDAO.dataBaseConfig, times(1)).getConnection();
        verify(ticketDAO.dataBaseConfig, times(1)).closePreparedStatement(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeConnection(null);
        assertFalse(result);
    }

    @Test
    public void checkIfCustomerHasHistory_verifyCorrectMethodsAreCalled() throws SQLException, IOException, ClassNotFoundException {
        //ARRANGE
        initTicket(ticket);

        //ACT
        boolean result = ticketDAO.checkIfCustomerHasHistory(ticket);

        //ASSERT
        verify(ticketDAO.dataBaseConfig, times(1)).getConnection();
        verify(ticketDAO.dataBaseConfig, times(1)).closePreparedStatement(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeResultSet(null);
        verify(ticketDAO.dataBaseConfig, times(1)).closeConnection(null);
        assertFalse(result);
    }
}

