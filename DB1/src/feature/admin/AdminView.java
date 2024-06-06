package feature.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

class SQLQueryAreaPanel extends JPanel {
	AdminViewModel viewModel;
	JTextArea textArea;
	private Table table;
	
	SQLQueryAreaPanel(AdminViewModel viewModel, Table table) {
		this.viewModel = viewModel;
		this.table = table;
		this.setLayout(new BorderLayout());
		
		this.textArea = new JTextArea();
		
		JLabel titleLabel = new JLabel("SQL 쿼리 입력");
		titleLabel.setFont(new Font(null, Font.BOLD, 20));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(titleLabel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel();
        
        JButton inputButton = new JButton("생성");
        inputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showConfirmDialog("생성하시겠습니까?")) {
					viewModel.inputButtonClicked(textArea.getText());
				}
			}
        });
        buttons.add(inputButton);
        
        JButton deleteButton = new JButton("삭제");
        deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showConfirmDialog("삭제하시겠습니까?")) {
					viewModel.deleteButtonClicked(textArea.getText());
				}
			}
        });
        buttons.add(deleteButton);
        
        JButton updateButton = new JButton("변경");
        updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showConfirmDialog("변경하시겠습니까?")) {
					viewModel.updateButtonClicked(textArea.getText());
				}
			}
        });
        buttons.add(updateButton);
        
        this.add(buttons, BorderLayout.SOUTH);
	}
	
	private boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirm Dialog", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            return true;
        } else {
        	return false;
        }
    }
	
	private void updateTable() {
		switch (this.viewModel.currentTable) {
		case MEMBER:
			this.table.setModel(this.viewModel.membersToTableModel());
			break;
		case MOVIE:
			this.table.setModel(this.viewModel.moviesToTableModel());
			break;
		case RESERVATION:
			this.table.setModel(this.viewModel.reservationsToTableModel());
			break;
		case SCHEDULE:
			this.table.setModel(this.viewModel.schedulesToTableModel());
			break;
		case SCREEN:
			this.table.setModel(this.viewModel.screensToTableModel());
			break;
		case SEAT:
			this.table.setModel(this.viewModel.seatsToTableModel());
			break;
		case TICKET:
			this.table.setModel(this.viewModel.ticketsToTableModel());
			break;
		}
	}
}

class TableHeaderRenderer extends DefaultTableCellRenderer {
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFont(new Font(null, Font.BOLD, 20));

        return c;
    }
}

class TableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFont(new Font(null, Font.PLAIN, 16));
        // 선택된 셀의 배경색과 텍스트 색상 설정
        if (isSelected) {
            c.setBackground(Color.LIGHT_GRAY);
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
        }

        return c;
    }
}

class Table extends JTable {
	Table(DefaultTableModel tableModel) {
		this.setModel(tableModel);
		this.setRowHeight(40);
		this.setDefaultRenderer(Object.class, new TableCellRenderer());
		
		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new TableHeaderRenderer());
	}
}

class TableButton extends JButton implements ActionListener {
	private AdminViewModel viewModel;
	private TableButtonType buttonType;
	private Table table;
	
	TableButton(AdminViewModel viewModel, TableButtonType buttonType, Table table) {
		this.viewModel = viewModel;
		this.buttonType = buttonType;
		this.table = table;
		this.setText(this.buttonType.getDescription());
		this.setFont(new Font(null, Font.PLAIN, 16));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.viewModel.tableButtonClicked(this.buttonType);
		
		switch (this.buttonType) {
		case MEMBER:
			this.table.setModel(this.viewModel.membersToTableModel());
			break;
		case MOVIE:
			this.table.setModel(this.viewModel.moviesToTableModel());
			break;
		case RESERVATION:
			this.table.setModel(this.viewModel.reservationsToTableModel());
			break;
		case SCHEDULE:
			this.table.setModel(this.viewModel.schedulesToTableModel());
			break;
		case SCREEN:
			this.table.setModel(this.viewModel.screensToTableModel());
			break;
		case SEAT:
			this.table.setModel(this.viewModel.seatsToTableModel());
			break;
		case TICKET:
			this.table.setModel(this.viewModel.ticketsToTableModel());
			break;
		}
		
		revalidate();
		repaint();
	}
}

class TableButtonsPanel extends JPanel {
	TableButtonsPanel(AdminViewModel viewModel, Table table) {
		TableButton memberButton = new TableButton(viewModel, TableButtonType.MEMBER, table);
		memberButton.doClick();
		this.add(memberButton);
		
		TableButton movieButton = new TableButton(viewModel, TableButtonType.MOVIE, table);
		this.add(movieButton);
		
		TableButton reservationButton = new TableButton(viewModel, TableButtonType.RESERVATION, table);
		this.add(reservationButton);
		
		TableButton scheduleButton = new TableButton(viewModel, TableButtonType.SCHEDULE, table);
		this.add(scheduleButton);
		
		TableButton screenButton = new TableButton(viewModel, TableButtonType.SCREEN, table);
		this.add(screenButton);
		
		TableButton seatButton = new TableButton(viewModel, TableButtonType.SEAT, table);
		this.add(seatButton);
		
		TableButton ticketButton = new TableButton(viewModel, TableButtonType.TICKET, table);
		this.add(ticketButton);
		
        this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
	}
}

public class AdminView extends JFrame {
	private final AdminViewModel viewModel;
	private Table table;
	
	public AdminView(AdminViewModel viewModel) {
		this.viewModel = viewModel;
		this.setSize(1920, 1080);
		
		this.table = new Table(viewModel.membersToTableModel());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.table);
		this.add(scrollPane, BorderLayout.CENTER);
		
		TableButtonsPanel buttonsPanel = new TableButtonsPanel(viewModel, this.table);
		this.add(buttonsPanel, BorderLayout.NORTH);
		
		SQLQueryAreaPanel sqlQueryTextAreaPanel = new SQLQueryAreaPanel(this.viewModel, this.table);
		this.add(sqlQueryTextAreaPanel, BorderLayout.WEST);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
}
