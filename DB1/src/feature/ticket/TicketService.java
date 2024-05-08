package feature.ticket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.ticket.Ticket;
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

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}