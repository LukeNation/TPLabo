package org.universidad.palermo.util;

import lombok.Data;
import lombok.Getter;
import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.controller.ProjectController;
import org.universidad.palermo.controller.TaskController;
import org.universidad.palermo.dao.EmployeeDao;
import org.universidad.palermo.dao.ProjectDao;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.service.EmployeeServiceImpl;
import org.universidad.palermo.service.ProjectServiceImpl;
import org.universidad.palermo.service.TaskServiceImpl;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.service.interfaces.TaskService;


public class MasterProjectManager {

    private static final IEmployeeDao employeeDao = new EmployeeDao();
    private static final EmployeeService employeeService = new EmployeeServiceImpl(employeeDao);
    @Getter
    private static final EmployeeController employeeController = new EmployeeController(employeeService);

    private static final IProjectDao projectDao = new ProjectDao();
    private static final ProjectService projectService = new ProjectServiceImpl(employeeService, projectDao);
    @Getter
    private static final ProjectController projectController = new ProjectController(projectService);

    private static final TaskService taskService = new TaskServiceImpl(employeeService);
    @Getter
    private static final TaskController taskController = new TaskController(taskService);

}
