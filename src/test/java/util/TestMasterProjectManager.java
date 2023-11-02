package util;

import lombok.Getter;
import org.universidad.palermo.controller.EmployeeController;
import org.universidad.palermo.controller.ProjectController;
import org.universidad.palermo.controller.TaskController;
import org.universidad.palermo.dao.EmployeeDao;
import org.universidad.palermo.dao.ProjectDao;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.service.EmployeeServiceImpl;
import org.universidad.palermo.service.ProjectServiceImpl;
import org.universidad.palermo.service.TaskServiceImpl;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.service.interfaces.TaskService;

@Getter
public class TestMasterProjectManager {

    private final IEmployeeDao employeeDao = new EmployeeDao();
    private final EmployeeService employeeService = new EmployeeServiceImpl(employeeDao);

    private final EmployeeController employeeController = new EmployeeController(employeeService);

    private final ProjectDao projectDao = new ProjectDao();
    private final ProjectService projectService = new ProjectServiceImpl(employeeService, projectDao);

    private final ProjectController projectController = new ProjectController(projectService);

    private final TaskService taskService = new TaskServiceImpl(employeeService);

    private final TaskController taskController = new TaskController(taskService);

    public void poblateEmployeeServiceTest() {
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        createEmployeeRequest.setName("firstName");
        createEmployeeRequest.setLastName("lastName");
        employeeService.create(createEmployeeRequest);
        createEmployeeRequest.setName("firstName2");
        createEmployeeRequest.setLastName("lastName2");
        employeeService.create(createEmployeeRequest);
    }
}
