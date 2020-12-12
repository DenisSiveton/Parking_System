package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        double inMinute = ticket.getInTime().getTime()/(60*1000);
        double outMinute = ticket.getOutTime().getTime()/(60*1000);

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double parkingTimeDuration = (outMinute - inMinute)/60;

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
}