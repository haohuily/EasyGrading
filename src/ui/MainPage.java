package ui;

import beans.*;
import utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
    private JButton btnViewClass;
    private static JFrame frame;


    private DefaultListModel listModel;
    int courseID;
    private int row;
    private int col;
    private String BUID;
    MainPage mainPage;


    List<String> columnsList;
    List<Component> components;
    List<Integer> componentIDs;
    Map<Integer, Component> componentMap;
    CourseUtils courseUtils;

//    Map<Component, Double> statistics = new HashMap<>();


    public MainPage() {
        mainPage = this;
        showList();

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
                if (courseID == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a course to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (BUID == null) {
                    JOptionPane.showMessageDialog(frame, "Please choose a student to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                StudentUtils studentUtils = new StudentUtils();
                boolean successful = studentUtils.deleteStudent(BUID);
                if (successful) {
                    showData();
                } else {
                    showData();
                }
            }
        });

        btnAddCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddClass(mainPage);
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
                if (courseID == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a course to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new ManageComponents(courseID, mainPage);
            }
        });

        btnAddStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseID == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a course to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new AddStudent(courseID, mainPage);
            }
        });

        btnGrading.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseID == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a course to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new GradingMain(courseID, mainPage);
            }
        });

        btnViewClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseID == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a course to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new Statistics(courseID);
            }
        });


        frame = new JFrame("Easy Grading");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(1100, 600);

        frame.setVisible(true);
    }


    public void showData() {
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
        StatisticUtils statisticUtils = new StatisticUtils();


        try {
            txtCourseName.setText(courseUtils.viewCertainCourse(courseID).getName());
        } catch (NullPointerException e) {
            System.out.println("Created");
        }


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

//                if (statistics.containsKey(currentComponent)) {
//                    statistics.put(currentComponent, statistics.get(currentComponent) + total);
//                } else {
//                    statistics.put(currentComponent, total);
//                }

                Statistic statistic = statisticUtils.searchCertainStatistic(student.getId(), componentID);
                if (statistic != null) {
                    statisticUtils.updateStatistic(statistic.getId(), total);
                } else {
                    statisticUtils.insertStatistic(student.getId(), componentID, total);
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

    public void showList() {
        listModel = new DefaultListModel();
        courseUtils = new CourseUtils();
        List<Course> courses = courseUtils.viewAllCourse();
        for (Course course : courses) {
            listModel.addElement(course.printCourse());
        }
        listCourses.setModel(listModel);
    }
}
