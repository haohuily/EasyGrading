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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
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
    private JButton btnRemoveStudent;
    private JLabel txtCourseName;
    private static JFrame frame;


    private DefaultListModel listModel;
    int courseID;
    private int row;
    private int col;
    private String BUID;


    List<String> columnsList;
    List<Component> components;
    List<Integer> componentIDs;
    Map<Integer, Component> componentMap;
    CourseUtils courseUtils;

    public MainPage() {
        listModel = new DefaultListModel();
        courseUtils = new CourseUtils();
        List<Course> courses = courseUtils.viewAllCourse();
        for (Course course : courses) {
            listModel.addElement(course.printCourse());
        }
        listCourses.setModel(listModel);

        listCourses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    showData();
                }
            }
        });


        tableGrades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 1) {
                    int row = tableGrades.getSelectedRow();
                    BUID = tableGrades.getValueAt(row, 1).toString();
                    System.out.println(BUID);
                }
            }
        });


        btnRemoveStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentUtils studentUtils = new StudentUtils();
                boolean successful = studentUtils.deleteStudent(BUID);
                if(successful){
                    showData();
                } else {
                    showData();
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
                new ManageComponents(courseID);
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
        frame.setSize(1000, 600);

        frame.setVisible(true);
    }



    public void showData(){
        columnsList = new ArrayList<>();
        components = new ArrayList<>();
        componentIDs = new ArrayList<>();
        componentMap = new HashMap<>();

        int idx = listCourses.getSelectedIndex();
        // TODO:courseID cannot be got in this way, just a tricky way here.
        courseID = idx + 1;

        ComponentUtils componentUtils = new ComponentUtils();
        GradingUtils gradingUtils = new GradingUtils();
        EnrollmentUtils enrollmentUtils = new EnrollmentUtils();

        txtCourseName.setText(courseUtils.viewCertainCourse(courseID).getName());

        components = componentUtils.searchAllComponents(courseID);

//        System.out.println(courseID);
        columnsList.add("Student name");
        columnsList.add("Student BU ID");
        columnsList.add("Student stand");

        for (Component component : components) {
            columnsList.add(component.getName());
            componentIDs.add(component.getId());
            componentMap.put(component.getId(), component);
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
            String stand = student.getStand();

            objectsList.add(name);
            objectsList.add(BUID);
            objectsList.add(stand);

            for (Integer componentID : componentIDs) {
                Grade grade = gradingUtils.findCertainGrade(student.getId(), componentID);

                Component currentComponent = componentMap.get(componentID);
                Double globalCurve = currentComponent.getGlobalCurve();
                Double graduateWeight = currentComponent.getGraduateWeight();
                Double undergraduateWeight = currentComponent.getUndergraduateWeight();

                Double total = 0.0;
                if (grade != null) {
                    Double baseGrade = grade.getStudentScore();
                    Double individualCurve = grade.getStudentCurve();


                    if (student.getStand().equals("Graduate")) {
                        total = (baseGrade + individualCurve + globalCurve) * graduateWeight / 100.0;
                    } else if (student.getStand().equals("Undergraduate")) {
                        total = (baseGrade + individualCurve + globalCurve) * undergraduateWeight / 100.0;
                    }
                    objectsList.add(total);
                } else {
                    objectsList.add(0.0);
                }

                sum += total;
            }

            DecimalFormat df = new DecimalFormat("0.00");
            objectsList.add(df.format(sum));

            if (sum >= 90) {
                objectsList.add("A");
            } else if (sum >= 85 && sum < 90) {
                objectsList.add("A-");
            } else if (sum >= 80 && sum < 85) {
                objectsList.add("B+");
            } else if (sum >= 75 && sum < 80) {
                objectsList.add("B");
            } else if (sum >= 70 && sum < 75) {
                objectsList.add("B-");
            } else if (sum < 70) {
                objectsList.add("Fail");
            }


            Object[] objects = objectsList.toArray();
            model.addRow(objects);

        }
        tableGrades.setModel(model);
    }
}
