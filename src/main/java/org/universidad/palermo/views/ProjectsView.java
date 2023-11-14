package org.universidad.palermo.views;

import org.universidad.palermo.Main;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.enums.ProjectStatus;
import org.universidad.palermo.util.MasterProjectManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProjectsView {

    private DefaultTableModel model = new DefaultTableModel();
    private JTable proyectos = new JTable(model);

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 0, 10));
        model.addColumn("Nro. Proyecto");
        model.addColumn("Titulo");
        model.addColumn("Descripcion");
        model.addColumn("Estado");
        model.addColumn("Empleados");
        model.addColumn("Tareas");

        updateTable();
        proyectos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        proyectos.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(proyectos);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton crearProyecto = new JButton("Nuevo Proyecto");
        crearProyecto.addActionListener(toNewProject);

        JButton modificarProyecto = new JButton("Modificar Proyecto");
        modificarProyecto.addActionListener(toModifyProject);

        JButton eliminarProyecto = new JButton("Eliminar Proyecto");
        eliminarProyecto.addActionListener(e -> {
            int row = proyectos.getSelectedRow();
            if (row != -1) {
                System.out.println(proyectos.getValueAt(row, 0));
                Long projectNumber = (Long) proyectos.getValueAt(row, 0);
                System.out.println(projectNumber);
                MasterProjectManager.getProjectController().delete(projectNumber);
                updateTable();
            }
        });

        JButton volver = new JButton("Volver");
        volver.addActionListener(StandardComponents.toMain);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 10));
        buttonPanel.add(modificarProyecto);
        buttonPanel.add(crearProyecto);
        buttonPanel.add(volver);
        buttonPanel.add(eliminarProyecto);

        volver.setMaximumSize(new Dimension(100, 50));
        crearProyecto.setMaximumSize(new Dimension(100, 50));

        panel.add(scrollPane);
        panel.add(buttonPanel);

        panel.setVisible(true);
        return panel;
    }

    public void updateTable() {
        model.setRowCount(0);
        List<ProjectResponse> projectList = MasterProjectManager.getProjectController().getAllProjects();
        if (projectList != null && !projectList.isEmpty()) {
            for (ProjectResponse project : projectList) {
                model.addRow(new Object[]{
                        project.getProjectNumber(),
                        project.getTitle(),
                        project.getDescription(),
                        ProjectStatus.getStatus(project.getStatus()).getDescription(),
                        project.getEmployeeList().size(),
                        project.getTaskList().size()});
            }
        }
        model.fireTableDataChanged();
    }

    public static Action toNewProject = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel titleLabel = new JLabel("Titulo");
            JTextField title = new JTextField();

            JLabel descriptionLabel = new JLabel("Descripcion");
            JTextField description = new JTextField();


            JFrame frame = new JFrame("Nuevo Proyecto");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateProjectRequest request = new CreateProjectRequest();
                    request.setTitle(title.getText());
                    request.setDescription(description.getText());
                    MasterProjectManager.getProjectController().createProject(request);
                    Main.projectsView.updateTable();
                    frame.dispose();
                }
            });
            JButton cancel = new JButton("Cancelar");
            cancel.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2, 10, 10));
            panel.add(titleLabel);
            panel.add(title);
            panel.add(descriptionLabel);
            panel.add(description);
            panel.add(cancel);
            panel.add(save);
            save.setText("Guardar");
            cancel.setText("Cancelar");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 150);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    };

    public static Action toModifyProject = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JLabel nroProjectLabel = new JLabel("nro Proyecto");
            JTextField nroProject = new JTextField();

            JLabel titleLabel = new JLabel("Titulo");
            JTextField title = new JTextField();

            JLabel descriptionLabel = new JLabel("Descripcion");
            JTextField description = new JTextField();

            JLabel statusLabel = new JLabel("Estado");
            JComboBox<String> status = new JComboBox<>();
            status.addItem("Pendiente");
            status.addItem("Bloqueado");
            status.addItem("En Progreso");
            status.addItem("Finalizado");
            status.addItem("Cancelado");

            JFrame frame = new JFrame("Modificar Proyecto");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UpdateProjectRequest request = new UpdateProjectRequest();
                    request.setProjectNumber(Long.parseLong(nroProject.getText()));
                    request.setTitle(title.getText());
                    request.setDescription(description.getText());
                    request.setStatus(status.getSelectedIndex());
                    MasterProjectManager.getProjectController().updateProject(request);
                    Main.projectsView.updateTable();
                    frame.dispose();
                }
            });

            JButton cancel = new JButton("Cancelar");
            cancel.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2, 10, 10));
            panel.add(nroProjectLabel);
            panel.add(nroProject);
            panel.add(titleLabel);
            panel.add(title);
            panel.add(descriptionLabel);
            panel.add(description);
            panel.add(statusLabel);
            panel.add(status);
            panel.add(cancel);
            panel.add(save);
            save.setText("Guardar");
            cancel.setText("Cancelar");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 200);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    };

}
