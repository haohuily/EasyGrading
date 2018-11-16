package ui;

import beans.Admin;
import utils.LoginUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login {
    private JButton btnLogin;
    private JButton btnRegister;
    private JTextField edtUsername;
    private JPasswordField edtPassword;
    private JPanel loginPanel;
    private JLabel txtTitle;
    private JLabel txtUsername;
    private JLabel txtPassword;
    private static JFrame frame;

    public Login() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtUsername.getText() == null || edtUsername.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter user name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtPassword.getText() == null || edtPassword.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter password", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LoginUtils loginUtils = new LoginUtils();
                String username = edtUsername.getText().trim();
                String password = edtPassword.getText().trim();
                Admin admin = new Admin(username, password);

                String result = loginUtils.login(admin);

                if (!result.equals("SuccessfullyLogin")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
//                    new mainBoard();
                    frame.dispose();
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtUsername.getText() == null || edtUsername.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter user name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtPassword.getText() == null || edtPassword.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter password", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LoginUtils loginUtils = new LoginUtils();
                String username = edtUsername.getText().trim();
                String password = edtPassword.getText().trim();
                Admin admin = new Admin(username, password);

                String result = loginUtils.register(admin);

                if (!result.equals("Registered")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    frame.dispose();
                }

            }
        });


        frame = new JFrame("Login");
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(400, 300);

        frame.setVisible(true);


        edtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    edtPassword.requestFocus();

                }
                super.keyPressed(e);
            }
        });
        edtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    btnLogin.doClick();

                }
                super.keyPressed(e);
            }
        });
    }

}
