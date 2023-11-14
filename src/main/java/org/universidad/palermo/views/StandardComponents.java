package org.universidad.palermo.views;

import org.universidad.palermo.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StandardComponents {

    public static Action close = new AbstractAction("Salir") {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    public static Action toProjects = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) Main.mainPanel.getLayout();
            cl.show(Main.mainPanel, "projectsView");
        }
    };

    public static Action toMain = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) Main.mainPanel.getLayout();
            cl.show(Main.mainPanel, "menuPrincipal");
        }
    };

    public static Action toEmployees = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) Main.mainPanel.getLayout();
            cl.show(Main.mainPanel, "employeesView");
        }
    };

    public static Action toTasks = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) Main.mainPanel.getLayout();
            cl.show(Main.mainPanel, "tasksView");
        }
    };


    public static Action toNewEmployee = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) Main.mainPanel.getLayout();
            cl.show(Main.mainPanel, "newEmployeeView");
            }
    };
    public static Dimension defaultMenuDim = new Dimension(200, 20);
}
