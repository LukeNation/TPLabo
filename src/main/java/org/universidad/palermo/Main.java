package org.universidad.palermo;

import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.util.MasterProjectManager;

public class Main {
    public static void main(String[] args) {

        EmployeeController employeeController = MasterProjectManager.getEmployeeController();

        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("Juan");
        request.setLastName("Perez");
        request.setSalary(100.0);
        employeeController.createEmployee(request);
        System.out.println("--------------------------------------------------");
        request.setName("Mateo");
        request.setLastName("Gomez");
        request.setSalary(200.0);
        employeeController.createEmployee(request);
        System.out.println("--------------------------------------------------");
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();
        updateRequest.setEmployeeNumber(2L);
        updateRequest.setName("Mateo");
        updateRequest.setLastName("Gomes");
        updateRequest.setSalary(200.0);
        employeeController.updateEmployee(updateRequest);
        System.out.println("--------------------------------------------------");
        employeeController.deleteEmployee(1L);
        employeeController.getEmployee(2L);
        System.out.println("--------------------------------------------------");
        request.setName("Martin");
        request.setLastName("Martinez");
        request.setSalary(500.0);
        employeeController.createEmployee(request);
        System.out.println("--------------------------------------------------");
        System.out.println("All Employees");
        employeeController.getAllEmployees();
        System.out.println("--------------------------------------------------");
        System.out.println("Free Employees");
        employeeController.getFreeEmployees();
        System.out.println("--------------------------------------------------");
        System.out.println("Assigned Employees");
        employeeController.getAssignedEmployees();
        System.out.println("--------------------------------------------------");
    }
}