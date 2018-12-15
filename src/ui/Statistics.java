package ui;

import beans.Component;
import beans.Course;
import utils.ComponentUtils;
import utils.CourseUtils;
import utils.StatisticUtils;
import utils.UICommonUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class Statistics {
    private JPanel mainPanel;
    private JLabel txtTitle;
    private JLabel txtClassName;
    private JLabel txtNumber;
    private JLabel txtInstructor;
    private JScrollPane scrollPanel;
    private JTable tableStatistic;
    private JPanel main;

    private JFrame frame;

    public Statistics(int courseID) {
        CourseUtils courseUtils = new CourseUtils();
        Course course = courseUtils.viewCertainCourse(courseID);

        txtClassName.setText(course.getName());
        txtNumber.setText(course.printCourse());

        ComponentUtils componentUtils = new ComponentUtils();
        List<Component> components = componentUtils.searchAllComponents(courseID);


        StatisticUtils statisticUtils = new StatisticUtils();


        Object[] columnNames = {"Component Name", "Type", "Total Score", "Average", "Highest", "Lowest", "Standard Deviation"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);


        for (Component component : components) {
            int id = component.getId();

            String name = component.getName();
            String type = component.getType();
            Double totalScore = component.getTotalScore();

            Double avg = new Double(statisticUtils.AVG(courseID, id).get("avg(curvedGrade)").toString());
            Double max = new Double(statisticUtils.MAX(courseID, id).get("max(curvedGrade)").toString());
            Double min = new Double(statisticUtils.MIN(courseID, id).get("min(curvedGrade)").toString());

            List<Map<String, Object>> listMap = statisticUtils.All(courseID, id);

            Double sum = 0.0;


            DecimalFormat df = new DecimalFormat("0.00");

            for (Map<String, Object> map : listMap) {
                Object object = map.get("curvedGrade");
                Double objectDouble = new Double(object.toString());

                sum += (objectDouble - avg) * (objectDouble - avg);

            }

            Double std = Math.sqrt(sum) / listMap.size();

            model.addRow(new Object[]{name, type, totalScore, df.format(avg), df.format(max), df.format(min), df.format(std)});
        }
        tableStatistic.setModel(model);


        frame = new JFrame("Statistics");
        frame.setContentPane(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 600);

        frame.setVisible(true);
    }
}
