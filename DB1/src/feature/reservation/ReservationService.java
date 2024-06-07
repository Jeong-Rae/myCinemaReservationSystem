package feature.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.domain.reservation.PaymentStatusType;
import core.domain.reservation.Reservation;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import core.domain.ticket.TicketRequest;
import feature.seat.SeatRequest;
import feature.seat.SeatService;
import feature.ticket.TicketService;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.ReservationRepository;
import infrastructure.repository.TicketRepository;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final SeatService seatService;

    public List<Reservation> findAllReservations() {
        List<Reservation> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = reservationRepository.findAllReservations(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    // 예약 생성
    public Reservation createReservation(ReservationRequest request) {
    	int amout = 0;
    	for (TicketRequest ticketRequest : request.ticketRequests()) {
    		amout += ticketRequest.salePrice();
    	}
    	
    	Reservation reservation = new Reservation(request.paymentMethodType(), PaymentStatusType.COMPLETED, amout, new Date());
    	reservation.setMemberId(request.memberId());
    	
    	Long reservationId;
    	
    	try (Connection connection = DatabaseConfig.getConnectionUser()) {
    		reservationId = reservationRepository.insertToReservation(connection, reservation);
    		reservation.setReservationId(reservationId);
    		
    		System.out.println("[createReservation] reservation: "+ reservation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	return reservation;
    	
    }
    
    //member 에약 조회
    public List<ReverationSummary> findReverationSummaryesByMemberId(long memberId) {
    	List<ReverationSummary> response = new ArrayList<ReverationSummary>();
    	try (Connection connection = DatabaseConfig.getConnectionUser()) {
    		response = reservationRepository.findReverationsByMemberId(connection, memberId);
    		
    		System.out.println("[findReverationSummaryes] memberId: " + memberId);
    		return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	return response;
    }
    
    // 예약 삭제
    public void deleteReservationAndTickets(Long reservationId) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnectionUser()) {
            // 트랜잭션 시작
            connection.setAutoCommit(false);

            try {
                // 연결된 티켓 삭제
                ticketRepository.deleteTicketsByReservationId(connection, reservationId);
                // 예약 삭제
                reservationRepository.deleteReservationById(connection, reservationId);

                // 트랜잭션 커밋
                connection.commit();
            } catch (SQLException e) {
                // 실패시 롤백
                connection.rollback();
                System.out.println("[deleteReservationAndTickets] 티켓 및 예약 삭제 실패");
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
    
    public ReservationResponse updateReservationWithTicket(Long reservationId, ReservationRequest request) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnectionUser()) {
            connection.setAutoCommit(false);

            Reservation reservation = reservationRepository.findById(connection, reservationId);
            System.out.println("[updateReservationWithTicket] " + reservation);
            try {
            	// 티켓 삭제
                ticketRepository.deleteTicketsByReservationId(connection, reservationId);

                // 새로운 티켓 생성
                List<Ticket> tickets = new ArrayList<>();
                for (TicketRequest ticketRequest : request.ticketRequests()) {
                    SeatRequest seatRequest = ticketRequest.seatRequest();
                    Seat seat = seatService.createSeat(connection, seatRequest);

                    Ticket ticket = ticketService.createTicket(connection, ticketRequest, seat, reservation);
                    tickets.add(ticket);
                }

                // 트랜잭션 커밋
                connection.commit();

                return new ReservationResponse(reservation, tickets);

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("[updateReservationWithTicket] 예약 정보 변경 실패");
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
    
    public void insertReservation(String insertData) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            reservationRepository.insertReservationBySqlNative(connection, insertData);
        } catch (SQLException e) {
            System.out.println("[insertReservation] 예약 등록 실패");
            throw e;
        }
    }

    public int updateReservation(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return reservationRepository.updateReservationBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateReservation] 예약 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteReservation(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return reservationRepository.deleteReservationBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteReservation] 예약 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }
    
    // member 의 예약정보 조회
    

    public ReservationService(ReservationRepository reservationRepository, TicketRepository ticketRepository, TicketService ticketService, SeatService seatService) {
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.seatService = seatService;
    }
}
