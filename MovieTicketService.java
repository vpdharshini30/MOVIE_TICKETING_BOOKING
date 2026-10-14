
package com.moviebooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.moviebooking.dao.MovieTicketDAO;
import com.moviebooking.model.MovieTicket;

@Service
public class MovieTicketService {

    @Autowired
    private MovieTicketDAO ticketDAO;

    // 1. Book Ticket
    public String bookTicket(MovieTicket ticket) {

        boolean available = ticketDAO.isSeatAvailable(
                ticket.getMovieName(),
                ticket.getSeatNumber());

        if (!available) {
            return "Seat Already Booked!";
        }

        try {
            int result = ticketDAO.bookTicket(ticket);

            if (result > 0) {
                return "Ticket Booked Successfully!";
            }

            return "Ticket Booking Failed!";

        } catch (DuplicateKeyException e) {
            return "Seat Already Booked!";
        }
    }

    // 2. View All Tickets
    public List<MovieTicket> viewAllTickets() {

        return ticketDAO.viewAllTickets();
    }

    // 3. Search Ticket
    public MovieTicket searchTicket(int ticketId) {

        return ticketDAO.searchTicket(ticketId);
    }

    // 4. Update Ticket
    public String updateTicket(
            int ticketId, MovieTicket ticket) {

        MovieTicket existingTicket =
                ticketDAO.searchTicket(ticketId);

        if (existingTicket == null) {
            return "Ticket Not Found!";
        }

        List<MovieTicket> tickets =
                ticketDAO.viewAllTickets();

        for (MovieTicket t : tickets) {

            if (t.getTicketId() != ticketId
                    && t.getMovieName().equals(ticket.getMovieName())
                    && t.getSeatNumber().equals(ticket.getSeatNumber())) {

                return "Seat Already Booked!";
            }
        }

        try {
            int result = ticketDAO.updateTicket(
                    ticketId, ticket);

            if (result > 0) {
                return "Ticket Updated Successfully!";
            }

            return "Ticket Update Failed!";

        } catch (DuplicateKeyException e) {
            return "Seat Already Booked!";
        }
    }

    // 5. Cancel Ticket
    public String cancelTicket(int ticketId) {

        int result = ticketDAO.cancelTicket(ticketId);

        if (result > 0) {
            return "Ticket Cancelled Successfully!";
        }

        return "Ticket Not Found!";
    }

    // 6. Check Seat Availability
    public String checkSeatAvailability(
            String movieName, String seatNumber) {

        boolean available =
                ticketDAO.isSeatAvailable(
                        movieName, seatNumber);

        if (available) {
            return "Seat is Available!";
        }

        return "Seat Already Booked!";
    }
}
