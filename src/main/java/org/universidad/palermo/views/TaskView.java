package org.universidad.palermo.views;

import org.springframework.util.StringUtils;
import org.universidad.palermo.Main;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.dto.response.TaskStatusResponse;
import org.universidad.palermo.enums.TaskStatusEnum;
import org.universidad.palermo.util.MasterProjectManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TaskView {

    private DefaultTableModel model = new DefaultTableModel();
    private final JTable tareas = new JTable(model);

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 0, 10));
        model.addColumn("Nro. tarea");
        model.addColumn("titulo");
        model.addColumn("descripcion");
        model.addColumn("estado");
        model.addColumn("tiempo estimado");
        model.addColumn("tiempo real");
        model.addColumn("proyecto");

        updateTable();
        tareas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tareas.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tareas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton crearProyecto = new JButton("Nueva Tarea");
        crearProyecto.addActionListener(toNewTask);

        JButton modificarProyecto = new JButton("Modificar Tarea");
        modificarProyecto.addActionListener(toModify);

        JButton eliminarProyecto = new JButton("Eliminar Tarea");
        eliminarProyecto.addActionListener(e -> {
            int row = tareas.getSelectedRow();
            if (row != -1) {
                Long taskNumber = (Long) tareas.getValueAt(row, 0);
                MasterProjectManager.getTaskController().delete(taskNumber);
                updateTable();
            }
        });

        JButton volver = new JButton("Volver");
        volver.addActionListener(StandardComponents.toMain);

        JButton detalle = new JButton("Detalle");
        detalle.addActionListener(taskDetail());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 20, 10));
        buttonPanel.add(modificarProyecto);
        buttonPanel.add(crearProyecto);
        buttonPanel.add(volver);
        buttonPanel.add(eliminarProyecto);
        buttonPanel.add(detalle);

        volver.setMaximumSize(new Dimension(100, 50));
        crearProyecto.setMaximumSize(new Dimension(100, 50));

        panel.add(scrollPane);
        panel.add(buttonPanel);

        panel.setVisible(true);
        return panel;
    }

    public void updateTable() {
        model.setRowCount(0);
        List<TaskResponse> taskResponses = MasterProjectManager.getTaskController().getAllTasks();
        if (taskResponses != null && !taskResponses.isEmpty()) {
            for (TaskResponse task : taskResponses) {
                model.addRow(new Object[]{
                        task.getTaskNumber(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getEstimatedHours(),
                        task.getWorkedHours(),
                        task.getProject() == null ? "" : task.getProject().getProjectNumber()
                });
            }
        }
        model.fireTableDataChanged();
    }

    public Action toModify = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JLabel nroTarea = new JLabel("nro tarea");
            JTextField tarea = new JTextField();

            JLabel tituloLabel = new JLabel("Titulo");
            JTextField titulo = new JTextField();

            JLabel descripcionLabel = new JLabel("descripcion");
            JTextField descripcion = new JTextField();

            JLabel tiempoEstimadoLabel = new JLabel("tiempo estimado");
            JTextField tiempoEstimado = new JTextField();

            JLabel tiempoRealLabel = new JLabel("tiempo real");
            JTextField tiempoReal = new JTextField();

            JLabel taskStatusLabel = new JLabel("estado");
            JComboBox<String> taskStatus = new JComboBox<>();
            for(TaskStatusEnum tse : TaskStatusEnum.values()){
                taskStatus.addItem(tse.getStatus() + " - " + tse.getDescription());
            }

            int row = Main.taskView.tareas.getSelectedRow();
            if (row != -1) {
                Long taskNumber = (Long) Main.taskView.tareas.getValueAt(row, 0);
                TaskResponse task = MasterProjectManager.getTaskController().getTask(taskNumber);
                if (task != null) {
                    tarea.setText(task.getTaskNumber().toString());
                    titulo.setText(task.getTitle());
                    descripcion.setText(task.getDescription());
                    tiempoEstimado.setText(task.getEstimatedHours().toString());
                    tiempoReal.setText(task.getWorkedHours().toString());
                    taskStatus.setSelectedItem(TaskStatusEnum.getStatusDescription(task.getStatus()).getStatus() + " - " + task.getStatus());
                }
            }

            JFrame frame = new JFrame("Modificar Tarea");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UpdateTaskRequest request = new UpdateTaskRequest();
                    request.setTaskNumber(Long.parseLong(tarea.getText()));
                    request.setTitle(titulo.getText());
                    request.setDescription(descripcion.getText());

                    request.setEstimatedHours(Double.parseDouble(tiempoEstimado.getText()));
                    request.setWorkedHours(Double.parseDouble(tiempoReal.getText()));
                    request.setStatus(Integer.parseInt(taskStatus.getItemAt(taskStatus.getSelectedIndex()).split(" - ")[0]));
                    MasterProjectManager.getTaskController().UpdateTask(request);
                    Main.taskView.updateTable();
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
            panel.setLayout(new GridLayout(7, 2, 10, 10));
            panel.add(nroTarea);
            panel.add(tarea);
            panel.add(tituloLabel);
            panel.add(titulo);
            panel.add(descripcionLabel);
            panel.add(descripcion);
            panel.add(tiempoEstimadoLabel);
            panel.add(tiempoEstimado);
            panel.add(tiempoRealLabel);
            panel.add(tiempoReal);
            panel.add(taskStatusLabel);
            panel.add(taskStatus);
            panel.add(cancel);
            panel.add(save);
            save.setText("Guardar");
            cancel.setText("Cancelar");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 250);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    };

    public static Action toNewTask = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JLabel tituloLabel = new JLabel("Titulo");
            JTextField titulo = new JTextField();

            JLabel descripcionLabel = new JLabel("descripcion");
            JTextField descripcion = new JTextField();

            JLabel tiempoEstimadoLabel = new JLabel("tiempo estimado");
            JTextField tiempoEstimado = new JTextField();

            JLabel proyectoLabel = new JLabel("proyecto");
            JComboBox<String> proyecto = new JComboBox<>();
            List<ProjectResponse> projectResponses = MasterProjectManager.getProjectController().getAllProjects();
            if (projectResponses != null && !projectResponses.isEmpty()) {
                for (ProjectResponse project : projectResponses) {
                    proyecto.addItem(project.getProjectNumber() + " - " + project.getTitle());
                }
            }

            JLabel empleadoLabel = new JLabel("empleado");
            JComboBox<String> empleado = new JComboBox<>();
            List<EmployeeResponse> employeeResponses = MasterProjectManager.getEmployeeController().getAllEmployees();
            if (employeeResponses != null && !employeeResponses.isEmpty()) {
                for (EmployeeResponse employee : employeeResponses) {
                    empleado.addItem(employee.getEmployeeNumber() + " - " + employee.getName());
                }
            }

            JFrame frame = new JFrame("Nueva Tarea");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateTaskRequest request = new CreateTaskRequest();
                    request.setTitle(titulo.getText());
                    request.setDescription(descripcion.getText());
                    Double tEstimado = StringUtils.hasText(tiempoEstimado.getText()) ? Double.parseDouble(tiempoEstimado.getText()) : 0D;
                    request.setEstimatedHours(tEstimado);
                    String empleadoId = empleado.getItemAt(empleado.getSelectedIndex()).split(" - ")[0];
                    request.setEmployeeNumber(empleadoId == null ? -1 : Long.parseLong(empleadoId));
                    String proyectoId = proyecto.getItemAt(proyecto.getSelectedIndex()).split(" - ")[0];
                    MasterProjectManager.getProjectController().createTask(Long.parseLong(proyectoId), request);
                    Main.taskView.updateTable();
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
            panel.setLayout(new GridLayout(6, 2, 10, 10));
            panel.add(tituloLabel);
            panel.add(titulo);
            panel.add(descripcionLabel);
            panel.add(descripcion);
            panel.add(tiempoEstimadoLabel);
            panel.add(tiempoEstimado);
            panel.add(proyectoLabel);
            panel.add(proyecto);
            panel.add(empleadoLabel);
            panel.add(empleado);
            panel.add(cancel);
            panel.add(save);
            save.setText("Guardar");
            cancel.setText("Cancelar");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 350);
            frame.getContentPane().add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    };

    public static Action taskDetail() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = Main.taskView.tareas.getSelectedRow();
                if (row != -1) {
                    Long taskNumber = (Long) Main.taskView.tareas.getValueAt(row, 0);
                    TaskResponse task = MasterProjectManager.getTaskController().getTask(taskNumber);
                    if (task != null) {
                        JLabel nroTareaLabel = new JLabel("nro Tarea");
                        JTextField nroTarea = new JTextField();
                        nroTarea.setText(task.getTaskNumber().toString());
                        nroTarea.setEditable(false);

                        JLabel tituloLabel = new JLabel("Titulo");
                        JTextField titulo = new JTextField();
                        titulo.setText(task.getTitle());
                        titulo.setEditable(false);

                        JLabel descripcionLabel = new JLabel("descripcion");
                        JTextField descripcion = new JTextField();
                        descripcion.setText(task.getDescription());
                        descripcion.setEditable(false);

                        JLabel tiempoEstimadoLabel = new JLabel("tiempo estimado");
                        JTextField tiempoEstimado = new JTextField();
                        tiempoEstimado.setText(task.getEstimatedHours().toString());
                        tiempoEstimado.setEditable(false);

                        JLabel tiempoRealLabel = new JLabel("tiempo real");
                        JTextField tiempoReal = new JTextField();
                        tiempoReal.setText(task.getWorkedHours().toString());
                        tiempoReal.setEditable(false);

                        JLabel proyectoLabel = new JLabel("proyecto");
                        JTextField proyecto = new JTextField();
                        proyecto.setText(task.getProject() == null ? "" : task.getProject().getTitle());
                        proyecto.setEditable(false);

                        JLabel empleadoLabel = new JLabel("empleado");
                        JTextField empleado = new JTextField();
                        empleado.setText(task.getAssignedEmployee() == null ? "" : task.getAssignedEmployee().getName());
                        empleado.setEditable(false);

                        JFrame frame = new JFrame("Detalle Tarea");
                        JButton cancel = new JButton("Cancelar");
                        cancel.setAction(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.dispose();
                            }
                        });

                        JButton historic = new JButton("Historico");
                        historic.setAction(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame frame = new JFrame("Historico");
                                DefaultTableModel model = new DefaultTableModel();
                                JTable tareas = new JTable(model);
                                model.addColumn("estado");
                                model.addColumn("fecha");
                                model.addColumn("descripcion");
                                model.addColumn("empleado");

                                List<TaskStatusResponse> taskStatusResponses = MasterProjectManager.getTaskController().getHistoric(task.getTaskNumber());
                                if (taskStatusResponses != null && !taskStatusResponses.isEmpty()) {
                                    for (TaskStatusResponse taskStatus : taskStatusResponses) {
                                        model.addRow(new Object[]{
                                                taskStatus.getStatus(),
                                                taskStatus.getChangeDate(),
                                                taskStatus.getDescription(),
                                                taskStatus.getEmployee() == null ? "" : taskStatus.getEmployee().getName()
                                        });
                                    }
                                }
                                frame.add(new JScrollPane(tareas));
                                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                frame.setSize(500,500);
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                            }
                        });

                        JPanel panel = new JPanel();
                        panel.setLayout(new GridLayout(8, 2, 10, 10));
                        panel.add(nroTareaLabel);
                        panel.add(nroTarea);
                        panel.add(tituloLabel);
                        panel.add(titulo);
                        panel.add(descripcionLabel);
                        panel.add(descripcion);
                        panel.add(tiempoEstimadoLabel);
                        panel.add(tiempoEstimado);
                        panel.add(tiempoRealLabel);
                        panel.add(tiempoReal);
                        panel.add(proyectoLabel);
                        panel.add(proyecto);
                        panel.add(empleadoLabel);
                        panel.add(empleado);
                        panel.add(cancel);
                        panel.add(historic);
                        cancel.setText("Cancelar");
                        historic.setText("Historico");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(300, 350);
                        frame.getContentPane().add(panel);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                }
            }
        };
    }
}
