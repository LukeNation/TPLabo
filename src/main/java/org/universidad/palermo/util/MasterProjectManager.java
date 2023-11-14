package org.universidad.palermo.util;

import lombok.Getter;
import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.controller.ProjectController;
import org.universidad.palermo.controller.TaskController;
import org.universidad.palermo.dao.EmployeeDao;
import org.universidad.palermo.dao.ProjectDao;
import org.universidad.palermo.dao.TaskDao;
import org.universidad.palermo.dao.TaskStatusDao;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.dao.interfaces.ITaskDao;
import org.universidad.palermo.dao.interfaces.ITaskStatusDao;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.mappers.ProjectMapper;
import org.universidad.palermo.mappers.TaskMapper;
import org.universidad.palermo.mappers.TaskStatusMapper;
import org.universidad.palermo.service.EmployeeServiceImpl;
import org.universidad.palermo.service.ProjectServiceImpl;
import org.universidad.palermo.service.TaskServiceImpl;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.service.interfaces.TaskService;


public class MasterProjectManager {

    private static final EmployeeMapper employeeMapper = new EmployeeMapper();
    private static final TaskMapper taskMapper = new TaskMapper(employeeMapper);
    private static final ProjectMapper projectMapper = new ProjectMapper(employeeMapper, taskMapper);
    private static final IEmployeeDao employeeDao = new EmployeeDao(employeeMapper);
    private static final EmployeeService employeeService = new EmployeeServiceImpl(employeeDao, employeeMapper);
    @Getter
    private static final EmployeeController employeeController = new EmployeeController(employeeService);
    private static final IProjectDao projectDao = new ProjectDao(projectMapper, employeeDao);
    private static final ITaskDao taskDao = new TaskDao(taskMapper);
    private static final TaskStatusMapper taskStatusMapper = new TaskStatusMapper();
    private static final ITaskStatusDao taskStatusDao = new TaskStatusDao(taskStatusMapper);

    private static final TaskService taskService = new TaskServiceImpl(employeeService,taskDao,taskStatusDao,taskMapper,taskStatusMapper);
    @Getter
    private static final TaskController taskController = new TaskController(taskService);
    private static final ProjectService projectService = new ProjectServiceImpl(employeeService, projectDao, projectMapper, employeeMapper, taskService);
    @Getter
    private static final ProjectController projectController = new ProjectController(projectService);

}
