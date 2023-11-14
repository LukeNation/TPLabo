package org.universidad.palermo;


import org.universidad.palermo.views.EmployeeView;
import org.universidad.palermo.views.MainFrame;
import org.universidad.palermo.views.ProjectsView;
import org.universidad.palermo.views.TaskView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JPanel mainPanel =  new JPanel();
    public static ProjectsView projectsView = new ProjectsView();
    public static EmployeeView employeeView = new EmployeeView();
    public static TaskView taskView = new TaskView();
    private static JFrame frame = new JFrame("Luke Portal");
    public static void main(String[] args) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        mainPanel.setLayout(new CardLayout());
        mainPanel.add(new MainFrame().getMainPanel(),"menuPrincipal");
        mainPanel.add(projectsView.getPanel(),"projectsView");
        mainPanel.add(employeeView.getPanel(),"employeesView");
        mainPanel.add(taskView.getPanel(),"tasksView");

        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

}