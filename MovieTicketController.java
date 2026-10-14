
package com.moviebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebooking.model.MovieTicket;
import com.moviebooking.service.MovieTicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class MovieTicketController {

    @Autowired
    private MovieTicketService ticketService;

    // 1. Book Ticket
    @PostMapping("/book")
    public String bookTicket(
            @Valid @RequestBody MovieTicket ticket) {

        return ticketService.bookTicket(ticket);
    }

    // 2. View All Tickets
    @GetMapping("/all")
    public List<MovieTicket> viewAllTickets() {

        return ticketService.viewAllTickets();
    }

    // 3. Search Ticket By ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> searchTicket(
            @PathVariable int ticketId) {

        MovieTicket ticket =
                ticketService.searchTicket(ticketId);

        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ticket Not Found!");
        }

        return ResponseEntity.ok(ticket);
    }

    // 4. Update Ticket
    @PutMapping("/update/{ticketId}")
    public String updateTicket(
            @PathVariable int ticketId,
            @Valid @RequestBody MovieTicket ticket) {

        return ticketService.updateTicket(ticketId, ticket);
    }

    // 5. Cancel Ticket
    @DeleteMapping("/cancel/{ticketId}")
    public String cancelTicket(
            @PathVariable int ticketId) {

        return ticketService.cancelTicket(ticketId);
    }

    // 6. Check Seat Availability
    @GetMapping("/availability")
    public String checkSeatAvailability(
            @RequestParam String movieName,
            @RequestParam String seatNumber) {

        return ticketService.checkSeatAvailability(
                movieName, seatNumber);
    }
}
