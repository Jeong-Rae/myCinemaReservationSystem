package core.domain.screeningschedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ScreeningSchedule {
	private Long scheduleId;
	private Date startDate;
	private String startTime; // "HH:MM:SS"
	private DayOfWeekType dayOfWeek;
	private int sessionNumber;
	private Long movieId;
	private Long screenId;

	public ScreeningSchedule(Date startDate, String startTime, DayOfWeekType dayOfWeek, int sessionNumber) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.dayOfWeek = dayOfWeek;
		this.sessionNumber = sessionNumber;
	}

	public Long getScheduleId() {
		return this.scheduleId;
	}
	
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
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

	public long getScreenId() {
		return this.screenId;
	}

	public void setScreenId(Long cinemaId) {
		this.screenId = cinemaId;
	}

	@Override
	public String toString() {
		return "ScreeningSchedule[" + "scheduleId=" + this.scheduleId + ", startDate=" + this.startDate + ", startTime="
				+ this.startTime + ", dayOfWeek='" + this.dayOfWeek + '\'' + ", sessionNumber=" + this.sessionNumber
				+ ", movieId=" + this.movieId + ", screenId=" + this.screenId + ']';
	}
	
	public static ScreeningSchedule RsToScreeningSchedule(ResultSet rs) throws SQLException {
	    Long scheduleId = rs.getLong("schedule_id");
	    Date startDate = rs.getDate("start_date");
	    String startTime = rs.getString("start_time");
	    DayOfWeekType dayOfWeek = DayOfWeekType.valueOf(rs.getString("day_of_week"));
	    int sessionNumber = rs.getInt("session_number");
	    Long movieId = rs.getLong("movie_id");
	    Long screenId = rs.getLong("screen_id");

	    ScreeningSchedule screeningSchedule = new ScreeningSchedule(startDate, startTime, dayOfWeek, sessionNumber);
	    screeningSchedule.setScheduleId(scheduleId);
	    screeningSchedule.setMovieId(movieId);
	    screeningSchedule.setScreenId(screenId);

	    return screeningSchedule;
	}
}
