package core.domain.screeningschedule;

import java.util.Date;

public class ScreeningSchedule {
	private Long scheduleId;
	private Date startDate;
	private String startTime; // "HH:MM:SS"
	private DayOfWeekType dayOfWeek;
	private int sessionNumber;
	private Long movieId;
	private Long cinemaId;

	public ScreeningSchedule(Date startDate, String startTime, DayOfWeekType dayOfWeek, int sessionNumber) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.dayOfWeek = dayOfWeek;
		this.sessionNumber = sessionNumber;
	}

	public Long getScheduleId() {
		return this.scheduleId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void updateStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void updateStartTime(String startTime) {
		this.startTime = startTime;
	}

	public DayOfWeekType getDayOfWeek() {
		return this.dayOfWeek;
	}

	public void updateDayOfWeek(DayOfWeekType dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getSessionNumber() {
		return this.sessionNumber;
	}

	public void updateSessionNumber(int sessionNumber) {
		this.sessionNumber = sessionNumber;
	}

	public long getMovieId() {
		return this.movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public long getCinemaId() {
		return this.cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	@Override
	public String toString() {
		return "ScreeningSchedule[" + "scheduleId=" + this.scheduleId + ", startDate=" + this.startDate + ", startTime="
				+ this.startTime + ", dayOfWeek='" + this.dayOfWeek + '\'' + ", sessionNumber=" + this.sessionNumber
				+ ", movieId=" + this.movieId + ", cinemaId=" + this.cinemaId + ']';
	}
}
