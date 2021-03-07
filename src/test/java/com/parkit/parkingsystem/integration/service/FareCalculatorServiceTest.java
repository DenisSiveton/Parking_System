package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        testInit(60, ParkingType.CAR);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    private void testInit(int i, ParkingType car) {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (i * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, car, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
    }

    @Test
    public void calculateFareBike(){
        testInit(60, ParkingType.BIKE);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareUnkownType(){
        testInit(60, null);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        testInit(45, ParkingType.BIKE);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        testInit(45, ParkingType.CAR);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithLessThanThirtyMinutesParkingTime(){
        testInit(30, ParkingType.CAR);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals((0 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanThirtyMinutesParkingTime(){
        testInit(36, ParkingType.CAR); // --> cas negatif pour 1ere fonctionnalité pour CAR
        fareCalculatorService.calculateFare(ticket, false); // test d'intégration car appel à plusieurs méthodes
        assertEquals((0.6 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareBikeWithLessThanThirtyMinutesParkingTime(){
        testInit(30, ParkingType.BIKE); // --> faire des tests pour les cas négatifs
        fareCalculatorService.calculateFare(ticket, false); // test d'intégration car appel à plusieurs méthodes
        assertEquals((0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareBikeWithMoreThanThirtyMinutesParkingTime(){
        testInit(36, ParkingType.BIKE); // --> cas negatif pour 1ere fonctionnalité pour BIKE
        fareCalculatorService.calculateFare(ticket, false); // test d'intégration car appel à plusieurs méthodes
        assertEquals((0.6 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }
}
