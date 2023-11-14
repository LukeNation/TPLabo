package org.universidad.palermo.service;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.mappers.ProjectMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.service.interfaces.TaskService;

import java.util.List;

@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final EmployeeService employeeService;
    private final ProjectMapper projectMapper;
    private final EmployeeMapper employeeMapper;
    private final IProjectDao projectDao;
    private final TaskService taskService;

    public ProjectServiceImpl(EmployeeService employeeService, IProjectDao projectDao, ProjectMapper projectMapper, EmployeeMapper employeeMapper, TaskService taskService) {
        this.employeeService = employeeService;
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
        this.employeeMapper = employeeMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectResponse create(CreateProjectRequest req) {
        Project project = projectMapper.toEntity(req);
        projectDao.save(project);

        return projectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getAll() {
        return projectMapper.toResponseList(projectDao.findAll());
    }

    @Override
    public ProjectResponse get(Long projectNumber) {
        Project project = projectDao.findById(projectNumber);
        if(project != null){
            List<TaskResponse> tasks = taskService.getTaskByProject(projectNumber);
            List<EmployeeResponse> employees = getEmployeeList(projectNumber);
            return projectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public void delete(Long projectNumber) {
        projectDao.delete(projectNumber);
    }

    @Override
    public ProjectResponse update(UpdateProjectRequest req) {
        Project project = projectDao.findById(req.getProjectNumber());
        if(project != null){
            projectMapper.updateEntity(req, project);
            projectDao.update(project);
            return projectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public ProjectResponse addEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projectDao.findById(projectNumber);
        if(project != null){
            project.getEmployeeList().add(employeeService.getRaw(employeeNumber));
            employeeService.assignEmployee(employeeNumber);
            projectDao.includeEmployee(projectNumber, employeeNumber);
            return projectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public ProjectResponse removeEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projectDao.findById(projectNumber);
        if(project != null){
            employeeService.unAssignEmployee(employeeNumber);
            projectDao.removeEmployee(projectNumber, employeeNumber);
            return get(projectNumber);
        }
        return null;
    }

    @Override
    public List<EmployeeResponse> getEmployeeList(Long projectNumber){
        Project project = projectDao.findById(projectNumber);
        if(project != null){
            return employeeMapper.toResponseList(project.getEmployeeList());
        }
        return null;
    }

    @Override
    public Long getProjectCount(){
        return projectDao.count();
    }

    @Override
    public Integer getEmployeeCount(Long projectNumber){
        return projectDao.countEmployeesInProject(projectNumber);
    }

    @Override
    public boolean existsProject(Long projectNumber){
        return projectDao.exists(projectNumber);
    }

    @Override
    public TaskResponse createTask(Long projectNumber, CreateTaskRequest request) {
        ProjectResponse project = get(projectNumber);
        if(project != null){
            System.out.println("Project found : " + project.getTitle());
            request.setProjectNumber(projectNumber);
            TaskResponse response = taskService.createTask(request);
            response.setProject(project);
            return response;
        }
        return null;
    }

    @Override
    public List<TaskResponse> getTasks(Long projectNumber) {
        ProjectResponse project = get(projectNumber);
        if(project != null){
            return taskService.getTaskByProject(projectNumber);
        }
        return null;
    }

}
