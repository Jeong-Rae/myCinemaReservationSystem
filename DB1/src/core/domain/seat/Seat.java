package core.domain.seat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Seat {
    private Long seatId;
    private boolean isActive;
    private int rowNumber;
    private int colNumber;
    private Long screenId;
    private Long screeningScheduleId;

    public Seat(boolean isActive, int rowNumber, int colNumber) {
        this.isActive = isActive;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }

    public long getSeatId() {
        return this.seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void updateIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void updateRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public void updateColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public long getScreenId() {
        return this.screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public Long getScreeningScheduleId() {
        return this.screeningScheduleId;
    }

    public void setScreeningScheduleId(Long screeningScheduleId) {
        this.screeningScheduleId = screeningScheduleId;
    }

    @Override
    public String toString() {
        return "Seat[" + "seatId=" + this.seatId + ", isActive=" + this.isActive +
                ", rowNumber=" + this.rowNumber + ", colNumber=" + this.colNumber +
                ", screenId=" + this.screenId + ", screeningScheduleId=" + this.screeningScheduleId + ']';
    }

    public static Seat RsToSeat(ResultSet rs) throws SQLException {
        Long seatId = rs.getLong("seat_id");
        boolean isActive = rs.getBoolean("is_active");
        int rowNumber = rs.getInt("row_number");
        int colNumber = rs.getInt("col_number");
        Long screenId = rs.getLong("screen_id");
        Long screeningScheduleId = rs.getLong("screening_schedule_id");

        Seat seat = new Seat(isActive, rowNumber, colNumber);
        seat.setSeatId(seatId);
        seat.setScreenId(screenId);
        seat.setScreeningScheduleId(screeningScheduleId);

        return seat;
    }
}