package com.anu4619.airline.repository;

import com.anu4619.airline.db.TicketInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TicketRepository {
    private Map<Long, TicketInfo> ticketTable;

    @PostConstruct
    public void init() {
        ticketTable = new HashMap<>();
    }

    public TicketInfo getTicket(Long ticketId) {
        return ticketTable.get(ticketId);
    }

    public TicketInfo bookTicket(TicketInfo ticketDetails) {

        for (TicketInfo ticket : ticketTable.values()) {
            if (ticket.getPassengerName().equals(ticketDetails.getPassengerName()) &&
                    ticket.getFlightId().equals(ticketDetails.getFlightId()) &&
                    ticket.getTravelDate().equals(ticketDetails.getTravelDate())) {
                return ticket;
            }
        }

        Long ticketId = generateUniqueTicketId();
        String bookingReference = "REF-" + UUID.randomUUID().toString().substring(0, 8);

        TicketInfo newTicket = ticketDetails.toBuilder()
                .id(ticketId)
                .bookingReference(bookingReference)
                .build();

        ticketTable.put(ticketId, newTicket);
        return newTicket;
    }


    public boolean cancelTicket(Long ticketId) {
        if (ticketTable.containsKey(ticketId)) {
            ticketTable.remove(ticketId);
            return true;
        }
        return false;
    }

    private Long generateUniqueTicketId() {
        return ticketTable.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }
}
