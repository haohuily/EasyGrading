package ui;

import beans.Course;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AddCourse {
    private JPanel addCoursePanel;
    private JButton btnAddNew;
    private JButton btnViewAll;
    private static JFrame frame;


    public AddCourse(){

        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseUtils courseUtils = new CourseUtils();
                // here is hard code, should get from ui elements.
                String num = "CS530";
                String name = "Algorithm";
                String section = "";
                String semester = "Spring";
                int year = 2018;
                Course course = new Course(num, name, section, semester, year);

                String result = courseUtils.addCourse(course);

                if (!result.equals("Successfully add course")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    frame.dispose();
                }
            }
        });

        btnViewAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseUtils courseUtils = new CourseUtils();
                courseUtils.viewAllCourse();
            }
        });


        frame = new JFrame("addCoursePanel");
        frame.setContentPane(addCoursePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);


    }


}
