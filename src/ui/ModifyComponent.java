package ui;

import beans.Component;
import utils.ComponentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyComponent {
    private JPanel mainPanel;
    private JPanel cpntPanel;
    private JLabel txtCpntName;
    private JTextField edtCpntName;
    private JLabel txtCpntWeight;
    private JTextField edtGradWeight;
    private JTextField edtUnderGradWeight;
    private JLabel txtTotalScore;
    private JTextField edtTotalScore;
    private JLabel txtComments;
    private JTextField edtComments;
    private JButton btnModify;
    private JComboBox boxCategory;

    private JFrame frame;
    private String type = "Homework";

    public ModifyComponent(Component component, ManageComponents manageComponents, MainPage mainPage) {

        edtCpntName.setText(component.getName());

        edtTotalScore.setText(component.getTotalScore().toString());

        edtComments.setText(component.getComments());

        boxCategory.addItem(component.getType());

        if (component.getType().equals("Homework")) {
            boxCategory.addItem("Quiz");
            boxCategory.addItem("Project");
            boxCategory.addItem("Attendance");
            boxCategory.addItem("Exam");
            boxCategory.addItem("Lab");
        } else if (component.getType().equals("Quiz")) {
            boxCategory.addItem("Homework");
            boxCategory.addItem("Project");
            boxCategory.addItem("Attendance");
            boxCategory.addItem("Exam");
            boxCategory.addItem("Lab");
        } else if (component.getType().equals("Project")) {
            boxCategory.addItem("Homework");
            boxCategory.addItem("Quiz");
            boxCategory.addItem("Attendance");
            boxCategory.addItem("Exam");
            boxCategory.addItem("Lab");
        } else if (component.getType().equals("Attendance")) {
            boxCategory.addItem("Homework");
            boxCategory.addItem("Quiz");
            boxCategory.addItem("Project");
            boxCategory.addItem("Exam");
            boxCategory.addItem("Lab");
        } else if (component.getType().equals("Exam")) {
            boxCategory.addItem("Homework");
            boxCategory.addItem("Quiz");
            boxCategory.addItem("Project");
            boxCategory.addItem("Attendance");
            boxCategory.addItem("Lab");
        } else if (component.getType().equals("Lab")) {
            boxCategory.addItem("Homework");
            boxCategory.addItem("Quiz");
            boxCategory.addItem("Project");
            boxCategory.addItem("Attendance");
            boxCategory.addItem("Exam");
        }


        edtGradWeight.setText(component.getGraduateWeight().toString());
        edtUnderGradWeight.setText(component.getUndergraduateWeight().toString());

        boxCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = boxCategory.getSelectedItem().toString();
            }
        });


        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (edtCpntName.getText() == null || edtCpntName.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter component name", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtGradWeight.getText() == null || edtGradWeight.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter graduate weight", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtUnderGradWeight.getText() == null || edtUnderGradWeight.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter undergraduate weight", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (edtTotalScore.getText() == null || edtTotalScore.getText().length() <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter total score", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                ComponentUtils componentUtils = new ComponentUtils();
                boolean successful = componentUtils.updateCertainComponent(component.getId(),edtCpntName.getText().trim(), type, Double.parseDouble(edtGradWeight.getText().trim()),
                        Double.parseDouble(edtUnderGradWeight.getText().trim()), Double.parseDouble(edtTotalScore.getText().trim()), edtComments.getText());
                if(successful) {
                    frame.dispose();
                    manageComponents.showComponents();
                    mainPage.showData();
                } else {
                    return;
                }
            }
        });


        frame = new JFrame("Modify component");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(500, 300);

        frame.setVisible(true);
    }
}
