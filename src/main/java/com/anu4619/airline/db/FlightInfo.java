package com.anu4619.airline.db;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class FlightInfo {
    private Long id;
    private String airline;
    private String source;
    private String destination;
    private List<ScheduleInfo> schedules;
}
