package ui;

import beans.Student;
import utils.GradingUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeForStudent {
    private JPanel mainPanel;
    private JLabel txtStudentInfo;
    private JLabel txtStudentName;
    private JLabel txtStand;
    private JButton btnSave;
    private JTextArea edtComments;
    private JTextField edtCurve;
    private JTextField edtGrade;
    private JPanel Body;
    private JFrame frame;


    public GradeForStudent(Student student, int componentID) {

        int studentID = student.getId();
        String BUID = student.getBUID();
        String name = student.getName();
        String stand = student.getStand();

        txtStudentName.setText(name);
        txtStand.setText(stand);


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradingUtils gradingUtils = new GradingUtils();
                gradingUtils.insertGradeForStudent(studentID, componentID, Double.valueOf(edtGrade.getText()),
                        Double.valueOf(edtCurve.getText()), edtComments.getText());
                frame.dispose();
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
