package org.universidad.palermo.views;

import javax.swing.*;

import static org.universidad.palermo.views.StandardComponents.close;
import static org.universidad.palermo.views.StandardComponents.toEmployees;
import static org.universidad.palermo.views.StandardComponents.toProjects;
import static org.universidad.palermo.views.StandardComponents.toTasks;

public class MainFrame {
    private JPanel mainPanel;
    private final JButton projectButton = new JButton("Proyectos");
    private final JButton employeeButton = new JButton("Empleados");
    private final JButton taskButton = new JButton("Tareas");
    private final JButton exitButton = new JButton("Salir");

    public JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        projectButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        employeeButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        taskButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(projectButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(employeeButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(taskButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(exitButton);

        projectButton.setAction(toProjects);
        projectButton.setText("Proyectos");
        employeeButton.setAction(toEmployees);
        employeeButton.setText("Empleados");
        taskButton.setAction(toTasks);
        taskButton.setText("Tareas");

        exitButton.setAction(close);
        return mainPanel;
    }
}
