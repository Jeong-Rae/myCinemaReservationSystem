import java.awt.Container;
import java.util.List;

import javax.swing.JFrame;

import core.domain.movie.Movie;
import core.domain.reservation.PaymentMethodType;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.TicketRequest;
import feature.admin.AdminController;
import feature.auth.AuthController;
import feature.auth.AuthService;
import feature.member.MemberService;
import feature.movie.MovieSearchView;
import feature.movie.MovieSearchViewModel;
import feature.movie.MovieSearchViewModelDelegate;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
import feature.reservation.ReservationRequest;
import feature.reservation.ReservationResponse;
import feature.reservation.ReservationService;
import feature.screen.ScreenService;
import feature.screeningschedule.ScreeningScheduleService;
import feature.seat.SeatRequest;
import feature.seat.SeatService;
import feature.ticket.TicketService;
import feature.user.UserController;
import infrastructure.repository.MemberRepository;
import infrastructure.repository.MovieRepository;
import infrastructure.repository.ReservationRepository;
import infrastructure.repository.ScreenRepository;
import infrastructure.repository.ScreeningScheduleRepository;
import infrastructure.repository.SeatRepository;
import infrastructure.repository.TicketRepository;
import infrastructure.repository.db2.*;

class DIContainer {
	MovieSearchViewModel movieSearchViewDependcies;
	
	DIContainer() {
//    	DatabaseInitializer dbinitializer = new DatabaseInitializer();
//    	dbinitializer.initializeDatabase();
    	
        MovieRepository movieRepository = new MovieRepository();
        MovieService movieService = new MovieService(movieRepository);

        ScreenRepository screenRepository = new ScreenRepository();
        ScreenService screenService = new ScreenService(screenRepository);

        ScreeningScheduleRepository screeningScheduleRepository = new ScreeningScheduleRepository();
        ScreeningScheduleService screeningScheduleService = new ScreeningScheduleService(screeningScheduleRepository);

        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository);

        SeatRepository seatRepository = new SeatRepository();
        SeatService seatService = new SeatService(seatRepository);

        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        ReservationRepository reservationRepository = new ReservationRepository();
        ReservationService reservationService = new ReservationService(reservationRepository);

        // 관리자 컨트롤러
        AdminController adminController = new AdminController(
                movieService,
                screenService,
                screeningScheduleService,
                ticketService,
                seatService,
                memberService,
                reservationService
        );
        
        // 사용자 컨트롤러
        UserController userController = new UserController(
                movieService,
                screenService,
                screeningScheduleService,
                ticketService,
                seatService,
                memberService,
                reservationService
        );
        
        
        // auth 컨트롤러
        AuthService authService = new AuthService(memberRepository);
        AuthController authController = new AuthController(authService); 
        
        authController.login("root", "1234");

        // 필터조회
        List<Movie> movies = userController.searchMovies(new SearchCriteria("기생충", null, null, null));

        System.out.println("Movies:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        

        // 상영일정 조회
        List<ScreeningSchedule> screeningSchedules = userController.getScreeningScheduleByMovieId(movies.get(0).getMovieId());
        
        System.out.println("\nScreening Schedules:");
        for (ScreeningSchedule screeningSchedule : screeningSchedules) {
            System.out.println(screeningSchedule);
        }

       
        // 선택 가능 좌석 확인
        List<Seat> seats = userController.getUnavailableSeats(1L);
        System.out.println("\nSeats:");
        for (Seat seat : seats) {
            System.out.println(seat);
        }
        
        // 예약진행
        SeatRequest seatRequest = new SeatRequest(5, 5, 33L, 1L);
        TicketRequest ticketRequest = new TicketRequest(12000, 12000, seatRequest);
        ReservationRequest reservationRequest = new ReservationRequest(List.of(ticketRequest), PaymentMethodType.CARD, 1L);
        ReservationResponse reservationResponse = userController.createReservation(reservationRequest);
        System.out.println(reservationResponse);
        
        this.movieSearchViewDependcies = new MovieSearchViewModel(movieService);
	}
}

class FrameCoordinator implements MovieSearchViewModelDelegate {
	MovieSearchView movieSearchView;
	
	FrameCoordinator(DIContainer diContainer) {
		MovieSearchViewModel movieSearchViewModel = diContainer.movieSearchViewDependcies;
		movieSearchViewModel.delegate = this;
		new MovieSearchView(movieSearchViewModel);
	}

	@Override
	public void movieTableCellTapped(Movie movie) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}

class RootFrame extends JFrame {
	MovieSearchView movieSearchView;
	
	RootFrame(DIContainer diContainer) {
		this.setTitle("영화관 예약 시스템");
		this.setSize(1920, 1080);
		Container contentPane = this.getContentPane();
		
		this.movieSearchView = new MovieSearchView(diContainer.movieSearchViewDependcies);
		contentPane.add(this.movieSearchView);
//		this.add(this.movieSearchView);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
}

public class Application {
    public static void main(String[] args) {
    	DIContainer diContainer = new DIContainer();
    	
    	FrameCoordinator coordinator = new FrameCoordinator(diContainer);
    	
//    	RootFrame rootFrame = new RootFrame(diContainer);
    }

}