package feature.ticket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.reservation.Reservation;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import core.domain.ticket.TicketRequest;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> findAllTickets() {
        List<Ticket> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = ticketRepository.findAllTickets(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    //티켓 예약
    public Ticket createTicket(TicketRequest request, Seat seat, Reservation reservation) {
    	Ticket ticket = new Ticket(false, request.standardPrice(), request.salePrice());
    	ticket.setSeatId(seat.getSeatId());
    	ticket.setScreenId(seat.getScreenId());
    	ticket.setScreeningScheduleId(seat.getScreeningScheduleId());
    	ticket.setReservationId(reservation.getReservationId());
    	
    	Long ticketId;
    	
    	try (Connection connection = DatabaseConfig.getConnectionUser()) {
            ticketId = ticketRepository.insertToTicket(connection, ticket);
            ticket.setTicketId(ticketId);
            
            System.out.println("[createTicket] ticket: " + ticket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	return ticket;
    	
    }

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}