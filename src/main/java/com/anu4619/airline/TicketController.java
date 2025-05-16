package com.anu4619.airline;

import com.anu4619.airline.db.TicketInfo;
import com.anu4619.airline.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping()
    public ResponseEntity<Map<String, String>> bookTicket(@RequestBody TicketInfo ticket) {
        Map<String, String> responseBody = new HashMap<>();

        if (ticket.getPassengerName() == null || ticket.getPassengerName().isEmpty()) {
            responseBody.put("message", "Passenger name is required.");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (ticket.getFlightId() == null) {
            responseBody.put("message", "Flight ID is required.");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (ticket.getTravelDate() == null || ticket.getTravelDate().isBefore(LocalDate.now())) {
            responseBody.put("message", "Valid travel date is required (must be today or later).");
            return ResponseEntity.badRequest().body(responseBody);
        }

        TicketInfo bookedTicket = ticketService.bookTicket(ticket);
        responseBody.put("message", "Ticket booked successfully!");
        responseBody.put("bookingReference", bookedTicket.getBookingReference());
        responseBody.put("ticketId", String.valueOf(bookedTicket.getId()));
        return ResponseEntity.ok(responseBody);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Ticket ID is required."));
        }

        TicketInfo ticket = ticketService.getTicket(id);
        if (ticket == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No ticket exists with this ID."));
        }

        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelTicket(@PathVariable Long id) {
        Map<String, String> responseBody = new HashMap<>();

        if (id == null) {
            responseBody.put("message", "Ticket ID is required.");
            return ResponseEntity.badRequest().body(responseBody);
        }

        boolean response = ticketService.cancelTicket(id);
        if (response) {
            responseBody.put("message", "Ticket Canceled Successfully!");
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("message", "Invalid Ticket ID");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
