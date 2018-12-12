package ui;

import beans.Component;
import beans.Course;
import beans.Grade;
import beans.Student;
import utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private JButton btnGrading;
    private JScrollPane scrollPanelGrades;
    private static JFrame frame;


    private DefaultListModel listModel;
    int courseID;
    List<String> columnsList;
    List<Component> components = new ArrayList<>();
    List<Integer> componentIDs = new ArrayList<>();
    Map<Integer, Component> map = new HashMap<>();

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
                if (!e.getValueIsAdjusting()) {
                    columnsList = new ArrayList<>();

                    int idx = listCourses.getSelectedIndex();
                    // TODO:courseID cannot be got in this way, just a tricky way here.
                    courseID = idx + 1;

                    ComponentUtils componentUtils = new ComponentUtils();
                    GradingUtils gradingUtils = new GradingUtils();
                    EnrollmentUtils enrollmentUtils = new EnrollmentUtils();


                    components = componentUtils.searchAllComponents(courseID);

//                    System.out.println(courseID);
                    columnsList.add("Student name");
                    columnsList.add("Student BU ID");

                    for (Component component : components) {
                        columnsList.add(component.getName());
                        componentIDs.add(component.getId());
                        map.put(component.getId(), component);
                    }

                    columnsList.add("Total grade");
                    columnsList.add("Level");


                    DefaultTableModel model = new DefaultTableModel(columnsList.toArray(), 0);
                    List<Object> objectsList;


                    List<Student> students = enrollmentUtils.searchAllStudent(courseID);
                    for (Student student : students) {
                        objectsList = new ArrayList<>();

                        Double sum = 0.0;

                        String name = student.getName();
                        String BUID = student.getBUID();

                        objectsList.add(name);
                        objectsList.add(BUID);

                        // TODO
                        for (Integer componentID : componentIDs) {
                            Grade grade = gradingUtils.findCertainGrade(student.getId(), componentID);
                            Double baseGrade = 0.0;
                            Double individualCurve = 0.0;
                            Double total = 0.0;

                            if (grade != null) {
                                baseGrade = grade.getStudentScore();
                                individualCurve = grade.getStudentCurve();
                                total = baseGrade + individualCurve;
                            }
                            objectsList.add(total);
                            sum += total;
                        }

                        objectsList.add(sum);
                        objectsList.add("A");


                        Object[] objects = objectsList.toArray();
                        model.addRow(objects);

                    }
                    tableGrades.setModel(model);
                }
            }
        });


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

        btnGrading.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GradingMain(courseID);
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
