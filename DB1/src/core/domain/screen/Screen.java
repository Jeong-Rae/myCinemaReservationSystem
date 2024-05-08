package core.domain.screen;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Screen {
	private Long screenId;
	private String name;
	private boolean isActive;
	private int seatRow;
	private int seatCol;

	public Screen(String name, boolean isActive, int seatRow, int seatCol) {
		this.name = name;
		this.isActive = isActive;
		this.seatRow = seatRow;
		this.seatCol = seatCol;
	}

	public long getScreenId() {
		return this.screenId;
	}
	
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getName() {
		return this.name;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void updateIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getSeatRow() {
		return this.seatRow;
	}

	public void updateSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatCol() {
		return this.seatCol;
	}

	public void updateSeatCol(int seatCol) {
		this.seatCol = seatCol;
	}

	@Override
	public String toString() {
		return "Cinema[" + "cinemaId=" + this.screenId + ", name=\"" + this.name + "\"" + ", isActive=" + this.isActive
				+ ", seatRow=" + this.seatRow + ", seatCol=" + this.seatCol + ']';
	}
	
	public static Screen RsToScreen(ResultSet rs) throws SQLException {
	    Long screenId = rs.getLong("screen_id");
	    String name = rs.getString("name");
	    boolean isActive = rs.getBoolean("is_active");
	    int rowSize = rs.getInt("row_size");
	    int colSize = rs.getInt("col_size");

	    Screen screen = new Screen(name, isActive, rowSize, colSize);
	    screen.setScreenId(screenId);

	    return screen;
	}
}
