package ui;

import beans.Student;
import utils.StudentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudent {
    private JPanel Header;
    private JTextField searchTextField;
    private JButton logout;
    private JPanel Body;
    private JPanel Classlist;
    private JButton addclass;
    private JPanel mainPanel;
    private JButton backButton;
    private JButton importButton;
    private JTextField textField1;
    private JButton browseComputerButton;
    private JButton importButton1;
    private JTextField edtStudentFirstName;
    private JTextField edtBUID;
    private JButton btnAdd;
    private JRadioButton radioBtnUnderGraduate;
    private JRadioButton radioBtnGraduate;
    private JList Classes;
    private JTextField edtStudentLastName;
    private JFrame frame;
    private String stand;

    public AddStudent(int courseID, MainPage mainPage) {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtnUnderGraduate);
        btnGroup.add(radioBtnGraduate);
        radioBtnUnderGraduate.setSelected(true);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtStudentFirstName.getText() == null || edtStudentFirstName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter student first name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtStudentLastName.getText() == null || edtStudentLastName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter student last name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtBUID.getText() == null || edtBUID.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter student BU ID", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (btnGroup.getSelection() == null) {
                    JOptionPane.showMessageDialog(frame, "Please select student stand", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                String firstName = edtStudentFirstName.getText().trim();
                String lastName = edtStudentLastName.getText().trim();
                String name = firstName + " " + lastName;

                String buid = edtBUID.getText().trim();
                if (radioBtnGraduate.isSelected()) {
                    stand = "Graduate";
                } else {
                    stand = "Undergraduate";
                }

                StudentUtils studentUtils = new StudentUtils();
                Student student = new Student(buid, name, stand);

                String result = studentUtils.addStudent(student, courseID);

                if (!result.equals("Successfully add student")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    frame.dispose();
                    mainPage.showData();
                }
            }
        });


        frame = new JFrame("Add student");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }


}
