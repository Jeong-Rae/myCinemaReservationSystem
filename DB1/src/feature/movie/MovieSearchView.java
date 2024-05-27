package feature.movie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	
	public MovieSearchTextFields(MovieSearchViewModel viewModel, JList list) {
		this.setLayout(new GridLayout(4, 1));
		this.title = new SearchTextFieldPanel("제목", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				viewModel.titleTextFieldKeyTyped(title.textField);
				list.setListData(viewModel.moviesToString());
				revalidate();
				repaint();
			}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});
		this.director = new SearchTextFieldPanel("감독", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				viewModel.directorTextFieldKeyTyped(director.textField);
				list.setListData(viewModel.moviesToString());
				revalidate();
				repaint();
			}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});
		this.actor = new SearchTextFieldPanel("배우", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				viewModel.actorTextFieldKeyTyped(actor.textField);
				list.setListData(viewModel.moviesToString());
				revalidate();
				repaint();
			}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});
		this.genre = new SearchTextFieldPanel("장르", new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				viewModel.genreTextFieldKeyTyped(genre.textField);
				list.setListData(viewModel.moviesToString());
				revalidate();
				repaint();
			}

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }
		});
		this.add(this.title);
		this.add(this.director);
		this.add(this.actor);
		this.add(this.genre);
	}
}

public class MovieSearchView extends JFrame implements ListSelectionListener {
	private final MovieSearchViewModel viewModel;
	private JList list;
	
	public MovieSearchView(MovieSearchViewModel viewModel) {
		this.setTitle("영화관 예약 시스템");
		this.setSize(1920, 1080);
		this.viewModel = viewModel;
		this.setLayout(new BorderLayout());
//		this.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
		
		JLabel title = new JLabel("영화 검색", JLabel.LEADING);
		title.setFont(new Font(null, Font.BOLD, 32));
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 32, 0));
		this.add(title, BorderLayout.NORTH);
		
		this.list = new JList(viewModel.moviesToString());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(this.list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.add(new MovieSearchTextFields(this.viewModel, this.list), BorderLayout.WEST);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			System.out.println("selected :"+list.getSelectedValue());
		}
	}
}
