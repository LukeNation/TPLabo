package org.universidad.palermo.util;

import lombok.Data;
import lombok.Getter;
import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.controller.ProjectController;
import org.universidad.palermo.service.EmployeeServiceImpl;
import org.universidad.palermo.service.ProjectServiceImpl;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;

@Data
public class MasterProjectManager {

    private static EmployeeService employeeService = new EmployeeServiceImpl();
    @Getter
    private static EmployeeController employeeController = new EmployeeController(employeeService);

    private static ProjectService projectService = new ProjectServiceImpl(employeeService);
    @Getter
    private static ProjectController projectController = new ProjectController(projectService);



}
