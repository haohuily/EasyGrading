package ui;

import beans.Course;
import beans.Student;
import utils.CourseUtils;
import utils.StudentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewStudent {
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
    private JTextField edtStudentName;
    private JTextField edtBUID;
    private JButton btnAdd;
    private JRadioButton radioBtnUnderGraduate;
    private JRadioButton radioBtnGraduate;
    private JList Classes;
    private JFrame frame;
    private String stand;

    public NewStudent() {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtnUnderGraduate);
        btnGroup.add(radioBtnGraduate);
        radioBtnUnderGraduate.setSelected(true);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtStudentName.getText() == null || edtStudentName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter student name", "Warning", JOptionPane.WARNING_MESSAGE);
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


                String name = edtStudentName.getText().trim();
                String buid = edtBUID.getText().trim();
                if(radioBtnGraduate.isSelected()){
                    stand = "Graduate";
                } else {
                    stand = "Undergraduate";
                }


                StudentUtils studentUtils = new StudentUtils();
                Student student = new Student(buid, name, stand);

                String result = studentUtils.addStudent(student);

                if (!result.equals("Successfully add student")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    frame.dispose();
                }
            }
        });


        frame = new JFrame("Body");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }


}
