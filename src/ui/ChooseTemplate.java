package ui;

import beans.Course;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseTemplate {
    private JPanel Classlist;
    private JButton btnChoose;
    private JList listCourses;
    private JFrame frame;


    private DefaultListModel listModel;
    int courseID;

    public ChooseTemplate() {

        listModel = new DefaultListModel();
        CourseUtils courseUtils = new CourseUtils();

        List<Course> courses = courseUtils.viewAllCourse();
        for (Course course : courses) {
            listModel.addElement(course.printCourse());
        }
        listCourses.setModel(listModel);


        listCourses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int idx = listCourses.getSelectedIndex();
                    // TODO:courseID cannot be got in this way, just a tricky way here.
                    courseID = idx + 1;
                }
            }
        });



        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course selectedCourse = courseUtils.viewCertainCourse(courseID);
                new ConfirmTemplate(selectedCourse);
                frame.dispose();
            }
        });




        frame = new JFrame("Classlist");
        frame.setContentPane(Classlist);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(300, 600);

        frame.setVisible(true);
    }
}
