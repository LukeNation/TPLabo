package org.universidad.palermo.views;

import org.universidad.palermo.Main;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.util.MasterProjectManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeView {

    private DefaultTableModel model = new DefaultTableModel();
    private final JTable empleados = new JTable(model);

    public JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1,0,10));
        model.addColumn("Nro. Empleado");
        model.addColumn("nombre");
        model.addColumn("sueldo");
        updateTable();
        empleados.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        empleados.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(empleados);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton crearProyecto = new JButton("Nuevo Empleado");
        crearProyecto.addActionListener(toNew);

        JButton modificarProyecto = new JButton("Modificar Empleado");
        modificarProyecto.addActionListener(toModify);

        JButton eliminarProyecto = new JButton("Eliminar Empleado");
        eliminarProyecto.addActionListener(e -> {
            int row = empleados.getSelectedRow();
            if(row != -1){
                Long employeeNumber = (Long) empleados.getValueAt(row, 0);
                MasterProjectManager.getEmployeeController().delete(employeeNumber);
                updateTable();
            }
        });

        JButton volver = new JButton("Volver");
        volver.addActionListener(StandardComponents.toMain);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2,20,10));
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

    public void updateTable(){
        model.setRowCount(0);
        List<EmployeeResponse> employeeResponses = MasterProjectManager.getEmployeeController().getAllEmployees();
        if(employeeResponses != null && !employeeResponses.isEmpty()) {
            for (EmployeeResponse project : employeeResponses) {
                model.addRow(new Object[]{
                        project.getEmployeeNumber(),
                        project.getName(),
                        project.getSalary()
                });
            }
        }
        model.fireTableDataChanged();
    }


    public static Action toNew = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel nombreLabel = new JLabel("Nombre");
            JTextField nombre = new JTextField();

            JLabel apellidoLabel = new JLabel("Apellido");
            JTextField apellido = new JTextField();

            JLabel sueldoLabel = new JLabel("Sueldo");
            JTextField sueldo = new JTextField();


            JFrame frame = new JFrame("Nuevo Empleado");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateEmployeeRequest request = new CreateEmployeeRequest();
                    request.setName(nombre.getText());
                    request.setLastName(apellido.getText());
                    request.setSalary(Double.valueOf(sueldo.getText()));
                    MasterProjectManager.getEmployeeController().createEmployee(request);
                    Main.employeeView.updateTable();
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
            panel.setLayout(new GridLayout(4, 2, 10, 10));
            panel.add(nombreLabel);
            panel.add(nombre);
            panel.add(apellidoLabel);
            panel.add(apellido);
            panel.add(sueldoLabel);
            panel.add(sueldo);
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

    public static Action toModify = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JLabel nroEmpleadoLabel = new JLabel("nro Empleado");
            JTextField nroEmpleado = new JTextField();

            JLabel nombreLabel = new JLabel("Nombre");
            JTextField nombre = new JTextField();

            JLabel apellidoLabel = new JLabel("Apellido");
            JTextField apellido = new JTextField();

            JLabel sueldoLabel = new JLabel("Sueldo");
            JTextField sueldo = new JTextField();

            JFrame frame = new JFrame("Modificar Empleado");
            JButton save = new JButton("Guardar");
            save.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UpdateEmployeeRequest request = new UpdateEmployeeRequest();
                    request.setEmployeeNumber(Long.parseLong(nroEmpleado.getText()));
                    request.setName(nombre.getText());
                    request.setLastName(apellido.getText());
                    request.setSalary(Double.parseDouble(sueldo.getText()));
                    MasterProjectManager.getEmployeeController().updateEmployee(request);
                    Main.employeeView.updateTable();
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
            panel.add(nroEmpleadoLabel);
            panel.add(nroEmpleado);
            panel.add(nombreLabel);
            panel.add(nombre);
            panel.add(apellidoLabel);
            panel.add(apellido);
            panel.add(sueldoLabel);
            panel.add(sueldo);
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
