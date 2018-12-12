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
    private JFrame frame;
    private DefaultListModel listModel;

    private String name;
    Component component;
    Student student;
    Map<String, Student> map = new HashMap<>();


    public GradingMain(int courseID) {

        listModel = new DefaultListModel();
        ComponentUtils componentUtils = new ComponentUtils();
        List<Component> components = componentUtils.searchAllComponents(courseID);

        for (Component component : components) {
            listModel.addElement(component.getName());

        }
        componentsList.setModel(listModel);

        componentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    name = componentsList.getSelectedValue().toString();
                    component = componentUtils.searchCertainComponent(courseID, name);

                    txtComponentName.setText(component.getName());
                    txtType.setText(component.getType());
                    txtGradWeight.setText(component.getGraduateWeight().toString());
                    txtUndergradWeight.setText(component.getUndergraduateWeight().toString());
                    txtGlobalCurve.setText(component.getGlobalCurve().toString());


                    // Table data change
                    Object[] columnNames = {"Student name", "Student BU ID", "Base Grade", "Individual Curve", "Comments"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                    EnrollmentUtils enrollmentUtils = new EnrollmentUtils();
                    List<Student> students = enrollmentUtils.searchAllStudent(courseID);
                    for (Student student : students) {
                        // TODO: more info here
                        String name = student.getName();
                        String BUID = student.getBUID();
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
                        model.addRow(new Object[]{name, BUID, baseGrade, individualCurve, comments});
                    }
                    tableGrade.setModel(model);


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
                new GradeForStudent(student, component.getId());
            }
        });


        btnSetCurve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetGlobalCurvePop(component.getId());
            }
        });


        frame = new JFrame("Body");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }
}
