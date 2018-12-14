package ui;

import utils.ComponentUtils;
import utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetGlobalCurvePop {
    private JLabel txtGlobalCurve;
    private JTextField edtCurve;
    private JButton btnSave;
    private JPanel Body;

    private JFrame frame;

    public SetGlobalCurvePop(int componentID) {


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentUtils componentUtils = new ComponentUtils();
                componentUtils.updateGlobalCurve(componentID, Double.valueOf(edtCurve.getText()));
                frame.dispose();
            }
        });

        frame = new JFrame("Set global curve");
        frame.setContentPane(Body);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(400, 300);

        frame.setVisible(true);
    }


}
