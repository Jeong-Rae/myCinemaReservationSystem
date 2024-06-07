package feature.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.domain.reservation.PaymentMethodType;
import core.domain.screeningschedule.ScreeningSchedule;
import feature.seat.SeatRequest;

class ReservationButton extends JPanel implements MouseListener {
	private ScreenViewModel viewModel;
	private boolean isActive;
	
	ReservationButton(ScreenViewModel viewModel) {
		this.viewModel = viewModel;
		JLabel label = new JLabel("예약하기");
		label.setFont(new Font(null, Font.BOLD, 28));
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(label);
		this.setBackground(Color.white);
		
        this.addMouseListener(this);
        
        this.isActive = !viewModel.selectedSeats.isEmpty();
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { 
		if (this.isActive) {
			this.setBackground(Color.LIGHT_GRAY);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (this.isActive) {
			this.setBackground(Color.white);
			this.viewModel.reservationButtonReleased();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
	
}

class PaymentMethodButton extends JButton implements ActionListener {
	private PaymentMethodType paymentMethod;
	private ScreenViewModel viewModel;
	
	PaymentMethodButton(ScreenViewModel viewModel, PaymentMethodType paymentMethod) {
		this.viewModel = viewModel;
		this.paymentMethod = paymentMethod;
		this.setText(this.paymentMethod.getDescription());
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.viewModel.paymentMethodButtonClicked(this.paymentMethod);
	}
}

class ReservationPanel extends JPanel {
	ReservationPanel(ScreenViewModel viewModel) {
		this.setLayout(new GridLayout(4, 1));
		JLabel totalPrice = new JLabel("결제 금액 : " + viewModel.totalPrice);
		totalPrice.setFont(new Font(null, Font.PLAIN, 20));
		this.add(totalPrice);
		
		JLabel paymentMethodLabel = new JLabel("결제 방식 선택");
		paymentMethodLabel.setFont(new Font(null, Font.PLAIN, 20));
		this.add(paymentMethodLabel);
		
		JPanel paymentMethodButtons = new JPanel();
		paymentMethodButtons.setLayout(new GridLayout(1, 3));
		paymentMethodButtons.add(new PaymentMethodButton(viewModel, PaymentMethodType.CARD));
		paymentMethodButtons.add(new PaymentMethodButton(viewModel, PaymentMethodType.CASH));
		paymentMethodButtons.add(new PaymentMethodButton(viewModel, PaymentMethodType.GIFTCON));
		this.add(paymentMethodButtons);
		
		this.add(new ReservationButton(viewModel));
	}
}

class SelectedSeatListListCellRenderer extends JPanel implements ListCellRenderer<SeatRequest> {
    private JLabel label;

    public SelectedSeatListListCellRenderer() {
        label = new JLabel();
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(label);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends SeatRequest> list, SeatRequest value, int index, boolean isSelected, boolean cellHasFocus) {
        label.setText("좌석 : 행" + value.rowNumber() + "열" + value.colNumber());
        this.label.setHorizontalAlignment(JLabel.LEADING);
        setOpaque(true);
        return this;
    }
}

class SelectedSeatListPanel extends JPanel {
	private ScreenViewModel viewModel;
	private JLabel title;
	private JList<SeatRequest> list;
	private ReservationPanel reservation;
	
	public SelectedSeatListPanel(ScreenViewModel viewModel) {
		this.viewModel = viewModel;
		this.setLayout(new BorderLayout());
		this.title = new JLabel("[" + this.viewModel.screen.getName() + "]" + " 선택 좌석", JLabel.LEADING);
		this.title.setFont(new Font(null, Font.BOLD, 32));
		this.title.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		this.add(this.title, BorderLayout.NORTH);
		
		DefaultListModel<SeatRequest> listModel = new DefaultListModel<>();
		viewModel.selectedSeats.forEach(seat -> listModel.addElement(seat));
		
		this.list = new JList<>(listModel);
        this.list.setCellRenderer(new SelectedSeatListListCellRenderer());
		
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(500, HEIGHT));
        this.add(scrollPane, BorderLayout.CENTER);
        
        this.reservation = new ReservationPanel(viewModel);
        this.add(this.reservation, BorderLayout.SOUTH);
	}
	
	public void updateListModel() {
		DefaultListModel<SeatRequest> listModel = new DefaultListModel<>();
		viewModel.selectedSeats.forEach(seat -> listModel.addElement(seat));
		this.list.setModel(listModel);
		
		this.remove(this.reservation);
		this.reservation = new ReservationPanel(viewModel);
        this.add(reservation, BorderLayout.SOUTH);
        this.title.setText("[" + this.viewModel.screen.getName() + "]" + " 선택 좌석");
	}
}

class ScheduleListCellRenderer extends JPanel implements ListCellRenderer<ScreeningSchedule> {
    private JLabel label;

    public ScheduleListCellRenderer() {
        setLayout(new BorderLayout());
        label = new JLabel();
        label.setFont(new Font(null, Font.PLAIN, 20));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(label);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ScreeningSchedule> list, ScreeningSchedule value, int index, boolean isSelected, boolean cellHasFocus) {
        label.setText(value.getStartDate().toString());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        setOpaque(true);
        return this;
    }
}

class ScheduleListPanel extends JPanel {
	private final ScreenViewModel viewModel;
	public JList<ScreeningSchedule> list;
	
	public ScheduleListPanel(ScreenViewModel viewModel) {
		this.viewModel = viewModel;
		
		DefaultListModel<ScreeningSchedule> listModel = new DefaultListModel<>();
		this.viewModel.schedules.forEach(schedule -> listModel.addElement(schedule));
		
		this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setCellRenderer(new ScheduleListCellRenderer());
        this.list.setSelectedIndex(0);
        this.setPreferredSize(new Dimension(200, 1080));

        // JScrollPane에 JList 추가
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(200, 1080));
        this.add(scrollPane);
	}
}

class SeatCellPanel extends JPanel implements MouseListener {
	private ScreenViewModel viewModel;
	private SelectedSeatListPanel selectedSeatList;
	private int rowNumber;
	private int colNumber;
	private boolean isActive;
	private boolean isHovering = false;
	private boolean isSelected;
	
	public SeatCellPanel(ScreenViewModel viewModel, SelectedSeatListPanel selectedSeatList, int rowNumber, int colNumber) {
		this.viewModel = viewModel;
		this.selectedSeatList = selectedSeatList;
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
		this.isActive = !viewModel.findUnvailableSeatFromMatrix(rowNumber, colNumber);
		this.isSelected = this.viewModel.findSelectedSeatFromMatrix(this.rowNumber, this.colNumber);
		
		this.addMouseListener(this);
		
		if (isActive) {
			this.setBackground(Color.white);
			this.setForeground(Color.black);
		} else {
			this.setForeground(Color.LIGHT_GRAY);
			this.setBackground(Color.DARK_GRAY);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { 
		if (!this.isActive) {
			return;
		}
		
		this.viewModel.seatCellReleased(rowNumber, colNumber);
		
		this.isSelected = this.viewModel.findSelectedSeatFromMatrix(rowNumber, colNumber);
		
		if (this.getBackground() == Color.white && this.getForeground() == Color.black) {
			this.setBackground(Color.blue);
			this.setForeground(Color.white);
		} else {
			this.setBackground(Color.white);
			this.setForeground(Color.black);
		}
		
		System.out.println("clicked: " + rowNumber + ", " + colNumber + ", " + this.isSelected);
		
		this.selectedSeatList.updateListModel();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!this.isActive) {
			return;
		}
		
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	@Override
	public void mouseExited(MouseEvent e) { 
		if (!this.isActive) {
			return;
		}
		
		this.setBorder(BorderFactory.createEmptyBorder());
	}
}

class SeatMatrixPanel extends JPanel {
	public SeatMatrixPanel(ScreenViewModel viewModel, SelectedSeatListPanel selectedSeatList) {
		this.setLayout(new GridLayout(viewModel.screen.getSeatRow(), viewModel.screen.getSeatCol(), 10, 10));
		
		for (int i = 1; i <= viewModel.screen.getSeatRow(); i++) {
			for (int j = 1; j <= viewModel.screen.getSeatCol(); j++) {
				this.add(new SeatCellPanel(viewModel, selectedSeatList, i, j));
			}
		}
		
		this.setSize(500, 500);
	}
}

class ScreenPanel extends JPanel {
	ScreenPanel(ScreenViewModel viewModel, SelectedSeatListPanel selectedSeatList) {
		this.setLayout(new BorderLayout(0, 100));

		JLabel screenLabel= new JLabel("SCREEN");
		screenLabel.setFont(new Font(null, Font.BOLD, 32));
		screenLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel screen = new JPanel();
		screen.setBackground(Color.LIGHT_GRAY);
		screen.add(screenLabel);
		
		this.add(screen, BorderLayout.NORTH);
		
		this.add(new SeatMatrixPanel(viewModel, selectedSeatList), BorderLayout.CENTER);
	}
}

public class ScreenView extends JFrame implements ListSelectionListener {
	private final ScreenViewModel viewModel;
	private ScheduleListPanel scheduleList;
	private ScreenPanel screen;
	private SelectedSeatListPanel selectedSeatList;
	
	public ScreenView(ScreenViewModel viewModel) {
		this.viewModel = viewModel;
		this.setTitle("좌석 선택");
		this.setSize(1920, 1080);
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("예약 하기");
		title.setFont(new Font(null, Font.BOLD, 32));
		title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(title, BorderLayout.NORTH);
		
		this.selectedSeatList = new SelectedSeatListPanel(this.viewModel);
		this.add(selectedSeatList, BorderLayout.EAST);
		
		this.screen = new ScreenPanel(this.viewModel, selectedSeatList);
		this.add(screen, BorderLayout.CENTER);
		
		this.scheduleList = new ScheduleListPanel(this.viewModel);
		this.scheduleList.list.addListSelectionListener(this);
		this.add(this.scheduleList, BorderLayout.WEST);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (!e.getValueIsAdjusting()) {
            this.viewModel.scheduleListCellTapped(this.scheduleList.list.getSelectedValue());
            
            this.remove(this.selectedSeatList);
            this.selectedSeatList = new SelectedSeatListPanel(this.viewModel);
    		this.add(selectedSeatList, BorderLayout.EAST);
            
            this.remove(this.screen);
            this.screen = new ScreenPanel(this.viewModel, selectedSeatList);
    		this.add(screen, BorderLayout.CENTER);
    		
    		revalidate();
    		repaint();
        }
	}
}
