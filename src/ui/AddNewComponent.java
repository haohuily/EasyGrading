package ui;

import utils.ComponentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.tools.internal.xjc.reader.Ring.add;

public class AddNewComponent {
    private JTextField edtCpntName;
    private JComboBox<String> boxCategory;
    private JTextField edtGradWeight;
    private JTextField edtUnderGradWeight;
    private JLabel txtCpntName;
    private JLabel txtCpntCategory;
    private JLabel txtCpntWeight;
    private JButton btnSave;
    private JPanel mainPanel;
    private JPanel cpntPanel;
    private JTextField edtTotalScore;
    private JLabel txtTotalScore;
    private JTextArea edtComments;
    private JLabel txtComments;
    private JFrame frame;

    private String type = "Homework";

    public AddNewComponent(int courseID, ManageComponents manageComponents, MainPage mainPage) {

        boxCategory.addItem("Homework");
        boxCategory.addItem("Quiz");
        boxCategory.addItem("Project");
        boxCategory.addItem("Attendance");
        boxCategory.addItem("Exam");
        boxCategory.addItem("Lab");

        boxCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = boxCategory.getSelectedItem().toString();
            }
        });


        btnSave.addActionListener(new ActionListener() {
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
                String result = componentUtils.addComponent(courseID, edtCpntName.getText().trim(), type, Double.parseDouble(edtGradWeight.getText().trim()),
                        Double.parseDouble(edtUnderGradWeight.getText().trim()), Double.parseDouble(edtTotalScore.getText().trim()), 0.0, edtComments.getText());


                if (!result.equals("Successfully add component")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    frame.dispose();
//                    new ManageComponents(courseID);
                    manageComponents.showComponents();
                    mainPage.showData();
                }
            }
        });

        frame = new JFrame("Add new component");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }
}
