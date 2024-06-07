package feature.auth;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ManagerLoginButton extends JButton implements ActionListener {
	private AuthViewModel viewModel;
	
	ManagerLoginButton(AuthViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.setText("관리자 로그인");
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!viewModel.managerLoginButtonClicked()) {
			JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class UserLoginButton extends JButton implements ActionListener {
	private AuthViewModel viewModel;
	
	UserLoginButton(AuthViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.setText("사용자 로그인");
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!viewModel.userLoginButtonClicked()) {
			JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class ButtonsPanel extends JPanel {
	ButtonsPanel(AuthViewModel viewModel) {
		this.setLayout(new GridLayout(1, 2));
		this.add(new ManagerLoginButton(viewModel));
		this.add(new UserLoginButton(viewModel));
	}
}

class TextFieldsPanel extends JPanel {
	private JTextField idField;
	private JTextField passwordField;
	
	TextFieldsPanel(AuthViewModel viewModel) {
		this.setLayout(new FlowLayout());
		
		this.idField = new JTextField(10);
		this.idField.setFont(new Font(null, Font.PLAIN, 20));
		this.idField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				viewModel.idFieldReleased(idField.getText());
			}
			
		});
		this.add(this.idField);
		
		this.passwordField = new JTextField(10);
		this.passwordField.setFont(new Font(null, Font.PLAIN, 20));
		this.passwordField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				viewModel.passwordFieldReleased(passwordField.getText());
			}
			
		});
		this.add(this.passwordField);
	}
}

public class AuthView extends JFrame {
	private final AuthViewModel viewModel;
	
	public AuthView(AuthViewModel viewModel) {
		this.viewModel = viewModel;
		this.setSize(300, 500);
		
		this.setLayout(new BorderLayout());
		this.add(new TextFieldsPanel(viewModel), BorderLayout.CENTER);
		this.add(new ButtonsPanel(viewModel), BorderLayout.SOUTH);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
