package feature.seat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import core.domain.seat.Seat;
import feature.screeningschedule.ScheduleListView;

class SeatCellPanel extends JPanel implements MouseListener {
	private SeatViewModel viewModel;
	private Seat seat;
	private boolean isHovering = false;
	private boolean isSelected = false;
	
	public SeatCellPanel(SeatViewModel viewModel, Seat seat) {
		this.viewModel = viewModel;
		this.seat = seat;
		this.setSize(50, 50);
		
		if (this.seat.getIsActive()) {
			this.setBackground(this.isSelected ? Color.blue : Color.white);
			this.setForeground(this.isSelected ? Color.white : Color.black);
		} else {
			this.setForeground(Color.LIGHT_GRAY);
			this.setBackground(Color.DARK_GRAY);
		}
		
		if (this.isHovering) {
			this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.viewModel.seatCellClicked(isSelected, seat);
		
		if (this.isSelected) {
			this.isSelected = false;
		} else {
			this.isSelected = true;
		}
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		this.isHovering = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) { 
		this.isHovering = false;
		repaint();
	}
}

class SeatMatrixPanel extends JPanel {
	public SeatMatrixPanel(SeatViewModel viewModel) {
		this.setLayout(new GridLayout(viewModel.row, viewModel.col, 10, 10));
		viewModel.seats.forEach(seat -> this.add(new SeatCellPanel(viewModel, seat)));
	}
}

public class SeatView extends JFrame {
	private final SeatViewModel viewModel;
	public SeatView(ScheduleListView scheduleListView, SeatViewModel viewModel) {
		this.viewModel = viewModel;
		this.setTitle(viewModel.movie.getTitle());
		this.setSize(1920, 1080);
		this.setLayout(new BorderLayout());
		
		this.add(scheduleListView, BorderLayout.WEST);
		
		this.add(new SeatMatrixPanel(viewModel), BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
