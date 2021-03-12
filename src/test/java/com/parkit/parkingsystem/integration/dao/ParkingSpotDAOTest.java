package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingSpotDAOTest {

    private static ParkingSpotDAO parkingSpotDAO;


    //@TestInstance(LifeCycle.PER_CLASS)

    @BeforeAll
    private static void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();

    }


    @Test
    public void getNextAvailableParkingSlotForCar_verifyCorrectMethodsAreCalled() throws SQLException, ClassNotFoundException {
        //ARRANGE
        DataBaseTestConfig dataBaseMockTestConfig = Mockito.mock(DataBaseTestConfig.class);
        parkingSpotDAO.dataBaseConfig = dataBaseMockTestConfig;
        //ACT
        parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        //ASSERT

        verify(dataBaseMockTestConfig, times(1)).getConnection();
        verify(dataBaseMockTestConfig, times(1)).closeResultSet(null);
        verify(dataBaseMockTestConfig, times(1)).closePreparedStatement(null);
        verify(dataBaseMockTestConfig, times(1)).closeConnection(null);
    }

    @Test
    public void updateParking_verifyCorrectMethodsAreCalled() throws SQLException, ClassNotFoundException {
        //ARRANGE
        DataBaseTestConfig dataBaseMockTestConfig = Mockito.mock(DataBaseTestConfig.class);
        parkingSpotDAO.dataBaseConfig = dataBaseMockTestConfig;

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        //ACT

        parkingSpotDAO.updateParking(parkingSpot);

        //ASSERT

        verify(dataBaseMockTestConfig, times(1)).getConnection();
        verify(dataBaseMockTestConfig, times(1)).closePreparedStatement(null);
        verify(dataBaseMockTestConfig, times(1)).closeConnection(null);
    }
}
