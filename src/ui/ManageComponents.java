package ui;

import beans.Component;
import utils.ComponentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageComponents {
    private JPanel Header;
    private JTextField searchTextField;
    private JButton logout;
    private JPanel Body;
    private JPanel Classlist;
    private JButton addclass;
    private JPanel mainPanel;
    private JList Classes;
    private JButton btnAddCpnts;
    private JButton btnDeleteCpnts;
    private JTable tableComponents;
    private JButton btnEditCpnt;
    private JFrame frame;

    //    private int row;
//    private int col;
    private int courseID;

    Component componentSelected;
    ComponentUtils componentUtils;
    DefaultTableModel model;

    ManageComponents manageComponents;

    Map<String, Component> map = new HashMap<>();

    public ManageComponents(int courseID, MainPage mainPage) {
        manageComponents = this;

        this.courseID = courseID;

        showComponents();

        btnAddCpnts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddNewComponent(courseID, manageComponents, mainPage);
            }
        });


        btnEditCpnt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(componentSelected == null) {
                    JOptionPane.showMessageDialog(frame, "Please choose a component to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                new ModifyComponent(componentSelected, manageComponents, mainPage);
            }
        });


        btnDeleteCpnts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(componentSelected == null) {
                    JOptionPane.showMessageDialog(frame, "Please choose a component to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String result = componentUtils.deleteComponents(componentSelected.getId());

                if (!result.equals("Successfully delete component")) {
                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    showComponents();
                    mainPage.showData();
                }

            }
        });


        frame = new JFrame("Manage components");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }


    public void showComponents() {
        componentUtils = new ComponentUtils();
        List<Component> components = componentUtils.searchAllComponents(courseID);


        Object[] columnNames = {"Component Name", "Type", "Graduate Weight", "Undergraduate Weight", "Total Score", "Notes"};
        model = new DefaultTableModel(columnNames, 0);

        for (Component component : components) {
            String name = component.getName();
            String type = component.getType();
            Double gradWeight = component.getGraduateWeight();
            Double underGradWeight = component.getUndergraduateWeight();
            Double totalScore = component.getTotalScore();
            String notes = component.getComments();

            map.put(name, component);

            model.addRow(new Object[]{name, type, gradWeight, underGradWeight, totalScore, notes});
        }
        tableComponents.setModel(model);


        tableComponents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 1) {
                    int row = tableComponents.getSelectedRow();
                    String name = tableComponents.getValueAt(row, 0).toString();
                    componentSelected = map.get(name);
                }

            }
        });
    }
}
