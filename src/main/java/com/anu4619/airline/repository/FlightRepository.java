package com.anu4619.airline.repository;

import com.anu4619.airline.db.FlightInfo;
import com.anu4619.airline.db.ScheduleInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlightRepository {
    private Map<Long, FlightInfo> flightTable;
    private Map<Long, List<ScheduleInfo>> scheduleTable;

    @PostConstruct
    public void init() {
        flightTable = new HashMap<>();
        scheduleTable = new HashMap<>();

        FlightInfo flight1 = FlightInfo.builder().id(1L).airline("Emirates").source("Dubai").destination("New York").schedules(new ArrayList<>()).build();
        FlightInfo flight2 = FlightInfo.builder().id(2L).airline("Qatar Airways").source("Doha").destination("London").schedules(new ArrayList<>()).build();
        FlightInfo flight3 = FlightInfo.builder().id(3L).airline("Singapore Airlines").source("Singapore").destination("Tokyo").schedules(new ArrayList<>()).build();
        FlightInfo flight4 = FlightInfo.builder().id(4L).airline("Lufthansa").source("Frankfurt").destination("Toronto").schedules(new ArrayList<>()).build();
        FlightInfo flight5 = FlightInfo.builder().id(5L).airline("Turkish Airlines").source("Istanbul").destination("Paris").schedules(new ArrayList<>()).build();
        FlightInfo flight6 = FlightInfo.builder().id(6L).airline("British Airways").source("London").destination("Los Angeles").schedules(new ArrayList<>()).build();
        FlightInfo flight7 = FlightInfo.builder().id(7L).airline("Etihad Airways").source("Abu Dhabi").destination("Sydney").schedules(new ArrayList<>()).build();

        flightTable.put(1L, flight1);
        flightTable.put(2L, flight2);
        flightTable.put(3L, flight3);
        flightTable.put(4L, flight4);
        flightTable.put(5L, flight5);
        flightTable.put(6L, flight6);
        flightTable.put(7L, flight7);

        scheduleTable.put(1L, Arrays.asList(
                ScheduleInfo.builder().id(201L).flightId(1L).date(LocalDate.of(2025, 4, 10)).departureTime(LocalTime.of(8, 00)).arrivalTime(LocalTime.of(16, 30)).availableSeats(70).build(),
                ScheduleInfo.builder().id(202L).flightId(1L).date(LocalDate.of(2025, 4, 11)).departureTime(LocalTime.of(14, 00)).arrivalTime(LocalTime.of(22, 30)).availableSeats(50).build()
        ));

        scheduleTable.put(2L, Arrays.asList(
                ScheduleInfo.builder().id(203L).flightId(2L).date(LocalDate.of(2025, 4, 10)).departureTime(LocalTime.of(10, 00)).arrivalTime(LocalTime.of(14, 00)).availableSeats(65).build(),
                ScheduleInfo.builder().id(204L).flightId(2L).date(LocalDate.of(2025, 4, 12)).departureTime(LocalTime.of(16, 00)).arrivalTime(LocalTime.of(20, 00)).availableSeats(55).build()
        ));

        scheduleTable.put(3L, Arrays.asList(
                ScheduleInfo.builder().id(205L).flightId(3L).date(LocalDate.of(2025, 4, 10)).departureTime(LocalTime.of(9, 30)).arrivalTime(LocalTime.of(14, 00)).availableSeats(40).build(),
                ScheduleInfo.builder().id(206L).flightId(3L).date(LocalDate.of(2025, 4, 13)).departureTime(LocalTime.of(13, 00)).arrivalTime(LocalTime.of(17, 30)).availableSeats(30).build()
        ));

        scheduleTable.put(4L, Arrays.asList(
                ScheduleInfo.builder().id(207L).flightId(4L).date(LocalDate.of(2025, 4, 11)).departureTime(LocalTime.of(7, 00)).arrivalTime(LocalTime.of(13, 00)).availableSeats(45).build(),
                ScheduleInfo.builder().id(208L).flightId(4L).date(LocalDate.of(2025, 4, 14)).departureTime(LocalTime.of(20, 00)).arrivalTime(LocalTime.of(2, 30)).availableSeats(60).build()
        ));

        scheduleTable.put(5L, Arrays.asList(
                ScheduleInfo.builder().id(209L).flightId(5L).date(LocalDate.of(2025, 4, 12)).departureTime(LocalTime.of(11, 00)).arrivalTime(LocalTime.of(14, 00)).availableSeats(35).build(),
                ScheduleInfo.builder().id(210L).flightId(5L).date(LocalDate.of(2025, 4, 15)).departureTime(LocalTime.of(18, 00)).arrivalTime(LocalTime.of(21, 30)).availableSeats(40).build()
        ));

        scheduleTable.put(6L, Arrays.asList(
                ScheduleInfo.builder().id(211L).flightId(6L).date(LocalDate.of(2025, 4, 13)).departureTime(LocalTime.of(9, 00)).arrivalTime(LocalTime.of(18, 00)).availableSeats(50).build(),
                ScheduleInfo.builder().id(212L).flightId(6L).date(LocalDate.of(2025, 4, 16)).departureTime(LocalTime.of(12, 00)).arrivalTime(LocalTime.of(21, 00)).availableSeats(55).build()
        ));

        scheduleTable.put(7L, Arrays.asList(
                ScheduleInfo.builder().id(213L).flightId(7L).date(LocalDate.of(2025, 4, 14)).departureTime(LocalTime.of(22, 00)).arrivalTime(LocalTime.of(12, 30)).availableSeats(75).build(),
                ScheduleInfo.builder().id(214L).flightId(7L).date(LocalDate.of(2025, 4, 17)).departureTime(LocalTime.of(23, 30)).arrivalTime(LocalTime.of(14, 00)).availableSeats(80).build()
        ));
    }


    public List<FlightInfo> getAllFlights(String sort) {
        return flightTable.values()
                .stream()
                .map(flight -> FlightInfo.builder()
                        .id(flight.getId())
                        .airline(flight.getAirline())
                        .source(flight.getSource())
                        .destination(flight.getDestination())
                        .schedules(Collections.emptyList()) // Ensure schedules are not returned
                        .build())
                .sorted(Comparator.comparing(FlightInfo::getId,
                        "asc".equalsIgnoreCase(sort) ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }



    public FlightInfo getOneFlight(Long flightId) {
        return flightTable.get(flightId);
    }

    public List<ScheduleInfo> getSchedule(Long flightId, LocalDate date) {
        return scheduleTable.getOrDefault(flightId, Collections.emptyList())
                .stream()
                .filter(schedule -> date == null || schedule.getDate().equals(date)) // Return all if date is null
                .collect(Collectors.toList());
    }

}
