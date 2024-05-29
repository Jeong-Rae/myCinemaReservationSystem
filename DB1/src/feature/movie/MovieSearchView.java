package feature.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

class MovieTableHeaderRenderer extends DefaultTableCellRenderer {
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFont(new Font(null, Font.BOLD, 20));

        return c;
    }
}

class MovieTableCellRenderer extends DefaultTableCellRenderer {
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

class MovieTable extends JTable {
	MovieTable(DefaultTableModel tableModel) {
		this.setModel(tableModel);
		this.setRowHeight(40);
		this.setDefaultRenderer(Object.class, new MovieTableCellRenderer());
		
		TableColumn titleColumn = this.getColumnModel().getColumn(0);
		titleColumn.setMinWidth(150);
		titleColumn.setMaxWidth(200);
		
		TableColumn durationColumn = this.getColumnModel().getColumn(1);
		durationColumn.setMinWidth(100);
		durationColumn.setMaxWidth(100);
		
		TableColumn ratingColumn = this.getColumnModel().getColumn(2);
		ratingColumn.setMinWidth(150);
		ratingColumn.setMaxWidth(150);
		
		TableColumn directorColumn = this.getColumnModel().getColumn(3);
		directorColumn.setMinWidth(80);
		directorColumn.setMaxWidth(90);
		
		TableColumn actorColumn = this.getColumnModel().getColumn(4);
		actorColumn.setMinWidth(80);
		actorColumn.setMaxWidth(90);
		
		TableColumn genreColumn = this.getColumnModel().getColumn(5);
		genreColumn.setMinWidth(80);
		genreColumn.setMaxWidth(90);
		
		TableColumn	releaseDateColumn = this.getColumnModel().getColumn(6);
		releaseDateColumn.setMinWidth(300);
		releaseDateColumn.setMaxWidth(300);
		
		TableColumn scoreColumn = this.getColumnModel().getColumn(7);
		scoreColumn.setMaxWidth(80);
		
		TableColumn descriptionColumn = this.getColumnModel().getColumn(8);
		descriptionColumn.setMinWidth(300);
		
		
		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new MovieTableHeaderRenderer());
		TableColumn durationHeader = header.getColumnModel().getColumn(1);
		durationHeader.setWidth(100);
	}
}

class SearchTextFieldPanel extends JPanel {
	JLabel title;
	JTextField textField;
	
	public SearchTextFieldPanel(String title, KeyListener keyListener) {
		this.title = new JLabel(title);
		this.textField = new JTextField(10);
		this.textField.addKeyListener(keyListener);
		this.title.setFont(new Font(null, Font.PLAIN, 20));
		this.textField.setFont(new Font(null, Font.PLAIN, 20));
		this.add(this.title);
		this.add(this.textField);
	}
}

class MovieSearchTextFields extends JPanel {
	SearchTextFieldPanel title;
	SearchTextFieldPanel director;
	SearchTextFieldPanel actor;
	SearchTextFieldPanel genre;
	
	public MovieSearchTextFields(MovieSearchViewModel viewModel, JTable table) {
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.title = new SearchTextFieldPanel("제목", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { 
				viewModel.titleTextFieldKeyReleased(title.textField);
				table.setModel(viewModel.moviesToTableModel());
				revalidate();
				repaint();
			}
		});
		this.director = new SearchTextFieldPanel("감독", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {
				viewModel.directorTextFieldKeyReleased(director.textField);
				table.setModel(viewModel.moviesToTableModel());
				revalidate();
				repaint();
			}
		});
		this.actor = new SearchTextFieldPanel("배우", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {
				viewModel.actorTextFieldKeyReleased(actor.textField);
				table.setModel(viewModel.moviesToTableModel());
				revalidate();
				repaint();
			}
		});
		this.genre = new SearchTextFieldPanel("장르", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { 
				viewModel.genreTextFieldKeyReleased(genre.textField);
				table.setModel(viewModel.moviesToTableModel());
				revalidate();
				repaint();
			}
		});
		this.add(this.title);
		this.add(this.director);
		this.add(this.actor);
		this.add(this.genre);
	}
}

class MovieSearchTitlePanel extends JPanel {
	MovieSearchTitlePanel(MovieSearchViewModel viewModel, JTable table) {
		this.setLayout(new GridLayout(2, 1));
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JLabel title = new JLabel("영화 검색", JLabel.LEADING);
		title.setFont(new Font(null, Font.BOLD, 32));
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 32, 0));
		this.add(title);
		this.add(new MovieSearchTextFields(viewModel, table));
	}
}

public class MovieSearchView extends JFrame implements ListSelectionListener {
	private final MovieSearchViewModel viewModel;
	private MovieTable table;
	
	public MovieSearchView(MovieSearchViewModel viewModel) {
		this.setTitle("영화관 예약 시스템");
		this.setSize(1920, 1080);
		this.viewModel = viewModel;
		this.setLayout(new BorderLayout());
		
		this.table = new MovieTable(viewModel.moviesToTableModel());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.table);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.add(new MovieSearchTitlePanel(this.viewModel, this.table), BorderLayout.NORTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			System.out.println("selected :" + table.getSelectedRow());
		}
	}
}
