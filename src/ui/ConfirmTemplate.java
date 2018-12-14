package ui;

import beans.Component;
import beans.Course;
import utils.ComponentUtils;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConfirmTemplate {
    private JPanel mainPanel;
    private JTextField edtCourseName;
    private JTextField edtCourseNum;
    private JTextField edtCourseSection;
    private JTextField edtSemester;
    private JTextField edtYear;
    private JButton btnContinue;

    private JFrame frame;
    List<Component> components = new ArrayList<>();
    int newCourseID;


    public ConfirmTemplate(Course selectedCourse, MainPage mainPage) {

        edtCourseName.setText(selectedCourse.getName());

        edtCourseNum.setText(selectedCourse.getNum());


        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtCourseNum.getText() == null || edtCourseNum.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter course number", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtCourseName.getText() == null || edtCourseName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter course name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtSemester.getText() == null || edtSemester.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter semester111", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtYear.getText() == null || edtYear.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter year", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String num = edtCourseNum.getText().trim();
                String name = edtCourseName.getText().trim();
                String section = edtCourseSection.getText().trim();
                String semester = edtSemester.getText().trim();
                int year = Integer.parseInt(edtYear.getText());



                CourseUtils courseUtils = new CourseUtils();
                Course course = new Course(num, name, section, semester, year);

                String result = courseUtils.addCourse(course);

                if (!result.equals("Successfully add course")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {

                    newCourseID = courseUtils.searchCertainCourseID(num, section, semester, year);

                    ComponentUtils componentUtils = new ComponentUtils();
                    components = componentUtils.searchAllComponents(selectedCourse.getId());

                    for (Component component : components) {
                        componentUtils.addComponent(newCourseID, component.getName(), component.getType(), component.getGraduateWeight(), component.getUndergraduateWeight(),
                                component.getTotalScore(), component.getGlobalCurve(), component.getComments());
                    }
                }
                frame.dispose();
                mainPage.showList();
                mainPage.showData();
            }
        });






        frame = new JFrame("Confirm template");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(500, 600);

        frame.setVisible(true);
    }
}
