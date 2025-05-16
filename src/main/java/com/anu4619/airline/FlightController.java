package com.anu4619.airline;

import com.anu4619.airline.db.FlightInfo;
import com.anu4619.airline.db.ScheduleInfo;
import com.anu4619.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping()
    public List<FlightInfo> getAllFlights(@RequestParam(defaultValue = "asc") String sort) {
        return flightService.getAllFlights(sort);
    }

    @GetMapping("/{id}")
    public FlightInfo getFlightById(@PathVariable Long id) {
        return flightService.getOneFlight(id);
    }

    @GetMapping("/{id}/schedules")
    public List<ScheduleInfo> getSchedule(@PathVariable Long id, @RequestParam(required = false) LocalDate dates) {
        return flightService.getSchedule(id, dates);
    }

}
