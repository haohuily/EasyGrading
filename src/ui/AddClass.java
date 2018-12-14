package ui;

import beans.Course;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClass {
    private JPanel mainPanel;
    private JTextField edtCourseName;
    private JTextField edtCourseNum;
    private JTextField edtCourseSection;
    private JTextField edtSemester;
    private JTextField edtYear;
    private JButton btnFromTemplate;
    private JButton btnFromScratch;
    private JPanel main;
    private static JFrame frame;


    public AddClass(MainPage mainPage) {
        btnFromScratch.addActionListener(new ActionListener() {
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
                    JOptionPane.showMessageDialog(frame, "Please enter semester", "Warning", JOptionPane.WARNING_MESSAGE);
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
                    frame.dispose();
                    mainPage.showList();
                }
            }
        });



        btnFromTemplate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseTemplate(mainPage);
                frame.dispose();
            }
        });


        frame = new JFrame("Add class");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(550, 450);

        frame.setVisible(true);

    }
}
