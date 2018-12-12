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
import java.util.List;

public class EditComponents {
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
    private JButton btnSaveAsTem;
    private JTable tableComponents;
    private JFrame frame;

    private int row;
    private int col;
    private String name;


    public EditComponents(int courseID) {

        btnAddCpnts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddNewComponent(courseID);
            }
        });


        ComponentUtils componentUtils = new ComponentUtils();
        List<Component> components = componentUtils.searchAllComponents(courseID);


        Object[] columnNames = {"Component Name", "Type", "Graduate Weight", "Undergraduate Weight", "Total Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Component component : components) {
            String name = component.getName();
            String type = component.getType();
            Double gradWeight = component.getGraduateWeight();
            Double underGradWeight = component.getUndergraduateWeight();
            Double totalScore = component.getTotalScore();

            model.addRow(new Object[]{name, type, gradWeight, underGradWeight, totalScore});
        }
        tableComponents.setModel(model);





        // TODO: delete multi rows in database
        // Delele multi rows on table but not database
//        btnDeleteCpnts.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int count[] = tableComponents.getSelectedRows();
//                if (count.length <= 0) {
//                    return;
//                } else {
//                    for (int i = count.length; i > 0; i--) {
//                        model.removeRow(tableComponents.getSelectedRow());
//                    }
//                }
//            }
//        });

//        btnDeleteCpnts.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String result = componentUtils.deleteComponents(courseID, name);
//
//                if (!result.equals("Successfully delete component")) {
//                    JOptionPane.showMessageDialog(frame, result, "Warning", JOptionPane.WARNING_MESSAGE);
//                    return;
//                } else {
//                    frame.dispose();
//                }
//                model.removeRow(tableComponents.getSelectedRow());
//            }
//        });

        tableComponents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                row = tableComponents.rowAtPoint(e.getPoint());
                col = tableComponents.columnAtPoint(e.getPoint());
                String name = tableComponents.getValueAt(row, col).toString();
                System.out.println(row);
                System.out.println(col);
                System.out.println(name);


            }
        });


        frame = new JFrame("mainPanel");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }
}
