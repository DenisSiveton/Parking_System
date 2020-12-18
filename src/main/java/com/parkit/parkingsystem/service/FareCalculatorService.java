package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        double parkingTimeDuration = calculateParkingDuration(ticket);

        applyFareRate(ticket, parkingTimeDuration);

        checkIfCustomerIsARegular(ticket);
    }

    public void applyFareRate(Ticket ticket, double parkingTimeDuration) {
        //Verify that parking time duration is less than or equal to 30 minutes (0.5 hour)
        if(parkingTimeDuration * 60 <= 30) {
            ticket.setPrice(0);
        }
        else {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(parkingTimeDuration * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(parkingTimeDuration * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }

    private double calculateParkingDuration(Ticket ticket){
        double inMinute = ticket.getInTime().getTime()/(60*1000);
        double outMinute = ticket.getOutTime().getTime()/(60*1000);

        //TODO: Some tests are failing here. Need to check if this logic is correct
        //Change int into double so that decimals wouldn't be lost which are the minutes.
        double parkingTimeDuration = (outMinute - inMinute)/60;

        return parkingTimeDuration;
    }

    public static boolean checkIfCustomerIsARegular(Ticket ticket) {
        boolean isCustomerRegular = false;
        //look in DB if previous ticket had the same REGISTRATION_NUMBER
        if (isCustomerRegular) {
            regularCustomerDiscount(ticket);
        }
        return isCustomerRegular;
    }

    public static void regularCustomerDiscount(Ticket ticket) {
        ticket.setPrice(ticket.getPrice() * 0.95);
    }
}