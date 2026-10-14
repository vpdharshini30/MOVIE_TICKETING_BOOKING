
package com.moviebooking.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.moviebooking.model.MovieTicket;

@Repository
public class MovieTicketDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. Book Ticket
    public int bookTicket(MovieTicket ticket) {

        String sql = "INSERT INTO movie_tickets "
                + "(movie_name, customer_name, seat_number, ticket_price) "
                + "VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                ticket.getMovieName(),
                ticket.getCustomerName(),
                ticket.getSeatNumber(),
                ticket.getTicketPrice());
    }

    // 2. View All Tickets
    public List<MovieTicket> viewAllTickets() {

        String sql = "SELECT * FROM movie_tickets";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            MovieTicket ticket = new MovieTicket();

            ticket.setTicketId(rs.getInt("ticket_id"));
            ticket.setMovieName(rs.getString("movie_name"));
            ticket.setCustomerName(rs.getString("customer_name"));
            ticket.setSeatNumber(rs.getString("seat_number"));
            ticket.setTicketPrice(rs.getDouble("ticket_price"));

            return ticket;
        });
    }

    // 3. Search Ticket By ID
    public MovieTicket searchTicket(int ticketId) {

        String sql = "SELECT * FROM movie_tickets "
                + "WHERE ticket_id = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> {

                        MovieTicket ticket = new MovieTicket();

                        ticket.setTicketId(rs.getInt("ticket_id"));
                        ticket.setMovieName(rs.getString("movie_name"));
                        ticket.setCustomerName(rs.getString("customer_name"));
                        ticket.setSeatNumber(rs.getString("seat_number"));
                        ticket.setTicketPrice(rs.getDouble("ticket_price"));

                        return ticket;
                    },
                    ticketId);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 4. Update Ticket
    public int updateTicket(int ticketId, MovieTicket ticket) {

        String sql = "UPDATE movie_tickets SET "
                + "movie_name = ?, "
                + "customer_name = ?, "
                + "seat_number = ?, "
                + "ticket_price = ? "
                + "WHERE ticket_id = ?";

        return jdbcTemplate.update(sql,
                ticket.getMovieName(),
                ticket.getCustomerName(),
                ticket.getSeatNumber(),
                ticket.getTicketPrice(),
                ticketId);
    }

    // 5. Cancel Ticket
    public int cancelTicket(int ticketId) {

        String sql = "DELETE FROM movie_tickets "
                + "WHERE ticket_id = ?";

        return jdbcTemplate.update(sql, ticketId);
    }

    // 6. Check Seat Availability
    public boolean isSeatAvailable(
            String movieName, String seatNumber) {

        String sql = "SELECT COUNT(*) FROM movie_tickets "
                + "WHERE movie_name = ? AND seat_number = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                movieName,
                seatNumber);

        return count != null && count == 0;
    }
}
