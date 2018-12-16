package ui;

import beans.Component;
import beans.Grade;
import beans.Student;
import utils.ComponentUtils;
import utils.EnrollmentUtils;
import utils.GradingUtils;
import utils.UICommonUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradingMain {
    private JPanel Body;
    private JPanel componentsPanel;
    private JPanel mainPanel;
    private JList componentsList;
    private JLabel txtTitle;
    private JLabel txtStudentInfo;
    private JLabel txtComponentName;
    private JLabel txtGradWeight;
    private JTable tableGrade;
    private JLabel txtType;
    private JLabel txtUndergradWeight;
    private JPanel GradePanel;
    private JLabel txtGlobalCurve;
    private JButton btnGradeStudent;
    private JButton btnSetCurve;
    private JScrollPane scrollPanel;
    private JTextField edtSearch;
    private JButton btnSearch;
    private JLabel txtTotalScore;
    private JFrame frame;
    private DefaultListModel listModel;

    private String name;
    Component component;
    Student student;
    Map<String, Student> map = new HashMap<>();
    GradingMain gradingMain;
    ComponentUtils componentUtils;
    int courseID;
    DefaultTableModel model;


    public GradingMain(int courseID, MainPage mainPage) {
        this.courseID = courseID;
        gradingMain = this;


        listModel = new DefaultListModel();
        componentUtils = new ComponentUtils();
        List<Component> components = componentUtils.searchAllComponents(courseID);

        for (Component component : components) {
            listModel.addElement(component.getName());

        }
        componentsList.setModel(listModel);

        componentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    showGrade();
                }

            }
        });


        tableGrade.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 1) {
                    int row = tableGrade.getSelectedRow();
                    String BUID = tableGrade.getValueAt(row, 1).toString();
                    student = map.get(BUID);
                    System.out.println(BUID);
                }

            }
        });

        btnGradeStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (student == null) {
                    JOptionPane.showMessageDialog(frame, "Please choose a student to grade!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new GradeForStudent(student, component.getId(), mainPage, gradingMain);
            }
        });


        btnSetCurve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (component.getId() == 0) {
                    JOptionPane.showMessageDialog(frame, "Please choose a component to continue!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new SetGlobalCurvePop(component.getId(), gradingMain);
            }
        });


        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                tableGrade.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter(edtSearch.getText().trim()));
            }
        });


        frame = new JFrame("Grading");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }


    public void showGrade() {
        name = componentsList.getSelectedValue().toString();
        component = componentUtils.searchCertainComponent(courseID, name);

        txtComponentName.setText(component.getName());
        txtType.setText(component.getType());
        txtGradWeight.setText(component.getGraduateWeight().toString());
        txtUndergradWeight.setText(component.getUndergraduateWeight().toString());
        txtTotalScore.setText(component.getTotalScore().toString());
        txtGlobalCurve.setText(component.getGlobalCurve().toString());


        // Table data change
        Object[] columnNames = {"Student name", "Student BU ID", "Stand", "Base Grade", "Individual Curve", "Comments"};
        model = new DefaultTableModel(columnNames, 0);

        EnrollmentUtils enrollmentUtils = new EnrollmentUtils();
        List<Student> students = enrollmentUtils.searchAllStudent(courseID);
        for (Student student : students) {
            // TODO: more info here
            String name = student.getName();
            String BUID = student.getBUID();
            String stand = student.getStand();

            map.put(BUID, student);


            GradingUtils gradingUtils = new GradingUtils();
            Grade grade = gradingUtils.findCertainGrade(student.getId(), component.getId());

            Double baseGrade = 0.0;
            Double individualCurve = 0.0;
            String comments = "";

            if (grade != null) {
                baseGrade = grade.getStudentScore();
                individualCurve = grade.getStudentCurve();
                comments = grade.getComments();
            }
            model.addRow(new Object[]{name, BUID, stand, baseGrade, individualCurve, comments});
        }
        tableGrade.setModel(model);
    }
}
