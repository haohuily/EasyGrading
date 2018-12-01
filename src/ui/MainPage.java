package ui;

import beans.Course;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainPage {
    private JTextField searchTextField;
    private JButton btnLogout;
    private JPanel Classlist;
    private JPanel Gradebook;
    private JButton btnAddCourse;
    private JPanel mainPanel;
    private JList listCourses;
    private JScrollPane scrollPanelCourses;
    private static JFrame frame;


    private DefaultListModel listModel;

    public MainPage() {
        listModel = new DefaultListModel();
        CourseUtils courseUtils = new CourseUtils();
        List<Course> courses = courseUtils.viewAllCourse();
        for(Course course : courses) {
            listModel.addElement(course.printCourse());
        }
        listCourses.setModel(listModel);


        btnAddCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewClass();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
            }
        });


        frame = new JFrame("mainPanel");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }
}
