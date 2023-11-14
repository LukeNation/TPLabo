package util;

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

@Getter
public class TestMasterProjectManager {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final TaskMapper taskMapper = new TaskMapper(employeeMapper);
    private final ProjectMapper projectMapper = new ProjectMapper(employeeMapper, taskMapper);
    private final IEmployeeDao employeeDao = new EmployeeDao(employeeMapper);
    private final EmployeeService employeeService = new EmployeeServiceImpl(employeeDao, employeeMapper);
    private final EmployeeController employeeController = new EmployeeController(employeeService);
    private final IProjectDao projectDao = new ProjectDao(projectMapper, employeeDao);
    private final ITaskDao taskDao = new TaskDao(taskMapper);
    private final TaskStatusMapper taskStatusMapper = new TaskStatusMapper();
    private final ITaskStatusDao taskStatusDao = new TaskStatusDao(taskStatusMapper);
    private final TaskService taskService = new TaskServiceImpl(employeeService,taskDao,taskStatusDao,taskMapper,taskStatusMapper);
    private final TaskController taskController = new TaskController(taskService);
    private final ProjectService projectService = new ProjectServiceImpl(employeeService, projectDao, projectMapper, employeeMapper, taskService);
    private final ProjectController projectController = new ProjectController(projectService);
}
