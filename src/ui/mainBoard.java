package ui;

import beans.Admin;
import beans.Student;
import utils.LoginUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class mainBoard {
    private JPanel mainBoardPanel;
    private JLabel txtBUID;
    private JTextField edtBUID;
    private JTextField edtName;
    private JRadioButton isGrad;
    private JRadioButton isUnderGrad;
    private JButton btnAddStudent;
    private JLabel txtName;
    private static JFrame frame;


    // TODO: UNFINISHED
    public mainBoard() {
        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtBUID.getText() == null || edtBUID.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter BU ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtName.getText() == null || edtName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter student name.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                LoginUtils loginUtils = new LoginUtils();
                String BUID = edtBUID.getText().trim();
                String studentName = edtName.getText().trim();

            }
        });


        JFrame frame = new JFrame("mainBoard");
        frame.setContentPane(new mainBoard().mainBoardPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
