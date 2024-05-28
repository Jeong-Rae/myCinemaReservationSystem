package feature.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import core.domain.movie.Movie;

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

        // 특정 조건에 따른 색상 변경 (예: 특정 값일 때)
        if (value != null && value.equals("Special")) {
            c.setBackground(Color.YELLOW);
            c.setForeground(Color.RED);
        }

        return c;
    }
}

class SearchTextFieldPanel extends JPanel {
	JLabel title;
	JTextField textField;
	
	public SearchTextFieldPanel(String title, KeyListener keyListener) {
		this.title = new JLabel(title);
		this.textField = new JTextField(10);
		this.textField.addKeyListener(keyListener);
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
	private JTable table;
	
	public MovieSearchView(MovieSearchViewModel viewModel) {
		this.setTitle("영화관 예약 시스템");
		this.setSize(1920, 1080);
		this.viewModel = viewModel;
		this.setLayout(new BorderLayout());
		
		this.table = new JTable(viewModel.moviesToTableModel());
		this.table.setRowHeight(40);
		this.table.setDefaultRenderer(Object.class, new MovieTableCellRenderer());
		
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
