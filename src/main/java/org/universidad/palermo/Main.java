package org.universidad.palermo;

import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.controller.ProjectController;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.util.MasterProjectManager;

public class Main {
    public static void main(String[] args) {

        EmployeeController employeeController = MasterProjectManager.getEmployeeController();

        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("name");
        request.setLastName("lastName");
        request.setSalary(1000.0);
        employeeController.createEmployee(request);

        }
}