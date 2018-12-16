package ui;

import beans.Component;
import beans.Student;
import utils.ComponentUtils;
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
    private JTextField edtComments;
    private JTextField edtCurve;
    private JTextField edtGrade;
    private JPanel Body;
    private JFrame frame;


    public GradeForStudent(Student student, int componentID, MainPage mainPage, GradingMain gradingMain) {

        int studentID = student.getId();
        String BUID = student.getBUID();
        String name = student.getName();
        String stand = student.getStand();

        txtStudentName.setText(name);
        txtStand.setText(stand);

        ComponentUtils componentUtils = new ComponentUtils();
        Component currentComponent = componentUtils.searchCertainComponentByID(componentID);

        Double totalScore = currentComponent.getTotalScore();


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradingUtils gradingUtils = new GradingUtils();
                String gradeStr = edtGrade.getText();

                Double grade = 0.0;

                if (!gradeStr.equals("")) {
                    if (gradeStr.charAt(0) == '-') {
                        if (isNumeric(gradeStr.substring(1))) {
                            grade = totalScore - Double.valueOf(gradeStr.substring(1));
                            if (grade < 0) {
                                JOptionPane.showMessageDialog(frame, "Please grade higher than 0!", "Warning", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter a numeric grade for this student!", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                    } else {
                        if (isNumeric(gradeStr)) {
                            grade = Double.valueOf(gradeStr);
                            if (grade > totalScore) {
                                JOptionPane.showMessageDialog(frame, "Please grade no more than the max grade!", "Warning", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter a numeric grade for this student!", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } else if (gradeStr.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a grade for this student!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                gradingUtils.insertGradeForStudent(studentID, componentID, grade,
                        Double.valueOf(edtCurve.getText()), edtComments.getText());
                frame.dispose();
                gradingMain.showGrade();
                mainPage.showData();
            }
        });


        frame = new JFrame("Grade for student");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(500, 250);

        frame.setVisible(true);
    }


    public boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
