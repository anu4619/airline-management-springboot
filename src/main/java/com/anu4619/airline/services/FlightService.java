package com.anu4619.airline.services;


import com.anu4619.airline.db.FlightInfo;
import com.anu4619.airline.db.ScheduleInfo;
import com.anu4619.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<FlightInfo> getAllFlights(String sort) {
        return flightRepository.getAllFlights(sort);
    }

    public FlightInfo getOneFlight(Long flightId) {
        return flightRepository.getOneFlight(flightId);
    }

    public List<ScheduleInfo> getSchedule(Long flightId, LocalDate date) {
        return flightRepository.getSchedule(flightId, date);
    }
}
