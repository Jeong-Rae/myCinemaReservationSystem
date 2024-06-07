package feature.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
	String placeholder = "*생성인 경우만* 예시 ('세종이', '010-8282-8282', 'sju@example.com')";
	
	SQLQueryAreaPanel(AdminViewModel viewModel, Table table) {
		this.viewModel = viewModel;
		this.table = table;
		this.setPreferredSize(new Dimension(400, HEIGHT));
		this.setLayout(new BorderLayout());
		
		this.textArea = new JTextArea();
		this.textArea.setText(placeholder);
        this.textArea.setForeground(Color.GRAY);
		this.textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });
		
		JLabel titleLabel = new JLabel("SQL 쿼리 입력");
		titleLabel.setFont(new Font(null, Font.BOLD, 20));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(titleLabel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel();
        
        JButton inputButton = new JButton("생성");
        inputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showConfirmDialog("생성하시겠습니까?")) {
					if(!viewModel.insertButtonClicked(textArea.getText())) {
						JOptionPane.showMessageDialog(inputButton, "INSERT에 실패하였습니다!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					updateTable();
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
					if(!viewModel.deleteButtonClicked(textArea.getText())) {
						JOptionPane.showMessageDialog(deleteButton, "DELETE에 실패했습니다!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					updateTable();
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
					if(!viewModel.updateButtonClicked(textArea.getText())) {
						JOptionPane.showMessageDialog(updateButton, "UPDATE에 실패했습니다!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					updateTable();
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
		
        this.textArea.setText("");
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
	SQLQueryAreaPanel sqlQueryAreaPanel;
	
	TableButton(AdminViewModel viewModel, TableButtonType buttonType, Table table, SQLQueryAreaPanel sqlQueryAreaPanel) {
		this.viewModel = viewModel;
		this.sqlQueryAreaPanel = sqlQueryAreaPanel;
		this.buttonType = buttonType;
		this.table = table;
		this.setText(this.buttonType.getDescription());
		this.setFont(new Font(null, Font.PLAIN, 16));
		this.addActionListener(this);
	}
	
	private void updatePlaceholder() {
		switch (this.viewModel.currentTable) {
		case MEMBER:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 ('세종이', '010-8282-8282', 'sju@example.com')";
			break;
		case MOVIE:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 ('Inception', '02:28:00', '15세이상관람가', 'Christopher Nolan', 'Leonardo DiCaprio', 'Sci-Fi', 'A thief who steals corporate secrets through the use of dream-sharing technology.', '2010-07-16', 8.8)";
			break;
		case RESERVATION:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 ('CARD', 'COMPLETED', 12000, '2024-06-07', 1)";
			break;
		case SCHEDULE:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 ('2024-06-10', '14:30:00', 'MON', 1, 1, 1)";
			break;
		case SCREEN:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 ('Screen 1', true, 10, 20)";
			break;
		case SEAT:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 (true, 5, 10, 1, 1)";
			break;
		case TICKET:
			this.sqlQueryAreaPanel.placeholder = "*생성인 경우만* 예시 (true, 1000, 900, 1, 1, 1, 1))";
			break;
		}
		
		this.sqlQueryAreaPanel.textArea.setText(this.sqlQueryAreaPanel.placeholder);
		this.sqlQueryAreaPanel.textArea.setForeground(Color.GRAY);
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
		
		this.updatePlaceholder();
		
		revalidate();
		repaint();
	}
}

class TableButtonsPanel extends JPanel {
	TableButtonsPanel(AdminViewModel viewModel, Table table, SQLQueryAreaPanel sqlQueryAreaPanel) {
		TableButton memberButton = new TableButton(viewModel, TableButtonType.MEMBER, table, sqlQueryAreaPanel);
		memberButton.doClick();
		this.add(memberButton);
		
		TableButton movieButton = new TableButton(viewModel, TableButtonType.MOVIE, table, sqlQueryAreaPanel);
		this.add(movieButton);
		
		TableButton reservationButton = new TableButton(viewModel, TableButtonType.RESERVATION, table, sqlQueryAreaPanel);
		this.add(reservationButton);
		
		TableButton scheduleButton = new TableButton(viewModel, TableButtonType.SCHEDULE, table, sqlQueryAreaPanel);
		this.add(scheduleButton);
		
		TableButton screenButton = new TableButton(viewModel, TableButtonType.SCREEN, table, sqlQueryAreaPanel);
		this.add(screenButton);
		
		TableButton seatButton = new TableButton(viewModel, TableButtonType.SEAT, table, sqlQueryAreaPanel);
		this.add(seatButton);
		
		TableButton ticketButton = new TableButton(viewModel, TableButtonType.TICKET, table, sqlQueryAreaPanel);
		this.add(ticketButton);
		
		JButton resetButton = new JButton("데이터 베이스 초기화");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showConfirmDialog("정말로 초기화 하시겠습니까?")) {
					viewModel.resetButtonClicked();
					switch (viewModel.currentTable) {
					case MEMBER:
						table.setModel(viewModel.membersToTableModel());
						break;
					case MOVIE:
						table.setModel(viewModel.moviesToTableModel());
						break;
					case RESERVATION:
						table.setModel(viewModel.reservationsToTableModel());
						break;
					case SCHEDULE:
						table.setModel(viewModel.schedulesToTableModel());
						break;
					case SCREEN:
						table.setModel(viewModel.screensToTableModel());
						break;
					case SEAT:
						table.setModel(viewModel.seatsToTableModel());
						break;
					case TICKET:
						table.setModel(viewModel.ticketsToTableModel());
						break;
					}
				}
			}
		});
		this.add(resetButton);
		
        this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
	}
	
	private boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirm Dialog", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            return true;
        } else {
        	return false;
        }
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
		
		SQLQueryAreaPanel sqlQueryTextAreaPanel = new SQLQueryAreaPanel(this.viewModel, this.table);
		this.add(sqlQueryTextAreaPanel, BorderLayout.WEST);
		
		TableButtonsPanel buttonsPanel = new TableButtonsPanel(viewModel, this.table, sqlQueryTextAreaPanel);
		this.add(buttonsPanel, BorderLayout.NORTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
}
