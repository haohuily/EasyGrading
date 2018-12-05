package ui;

import beans.Component;
import beans.Course;
import utils.ComponentUtils;
import utils.CourseUtils;
import utils.UICommonUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JButton btnEdtClassCpnts;
    private JButton btnAddStudent;
    private JTable tableGrades;
    private JButton btnTest;
    private static JFrame frame;


    private DefaultListModel listModel;
    int courseID;

    public MainPage() {
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
                int idx = listCourses.getSelectedIndex();
                courseID = idx + 1;



                ComponentUtils componentUtils = new ComponentUtils();
                List<Component> components = componentUtils.searchAllTypes(courseID);
                List<String> types = new ArrayList<>();

                for (Component component : components) {
                    types.add(component.getType());
                }
                Object[] columnNames = types.toArray();

                for (int i = 0; i < columnNames.length; i++) {
                    System.out.println("--------------");
                    System.out.println(columnNames[i]);
                }

                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                tableGrades.setModel(model);
            }
        });




//        for (Component component : components) {
//            String name = component.getName();
//            String type = component.getType();
//            Double gradWeight = component.getGraduateWeight();
//            Double underGradWeight = component.getUndergraduateWeight();
//            Double totalScore = component.getTotalScore();
//
//            model.addRow(new Object[]{name, type, gradWeight, underGradWeight, totalScore});
//        }
//        tableComponents.setModel(model);


        btnAddCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddClass();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispose();
            }
        });

        btnEdtClassCpnts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditComponents(courseID);
            }
        });

        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudent(courseID);
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
