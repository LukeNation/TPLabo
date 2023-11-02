package org.universidad.palermo.service;

import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.mappers.ProjectMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private final EmployeeService employeeService;

    private Long projectNumber;
    private final IProjectDao projects;

    public ProjectServiceImpl(EmployeeService employeeService, IProjectDao projectDao) {
        this.employeeService = employeeService;
        this.projects = projectDao;
        projectNumber = projects.count();

    }

    @Override
    public ProjectResponse create(CreateProjectRequest req) {
        Project project = ProjectMapper.toEntity(req);
        projects.save(project);

        return ProjectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getAll() {
        return ProjectMapper.toResponseList(projects.findAll());
    }

    @Override
    public ProjectResponse get(Long projectNumber) {
        return ProjectMapper.toResponse(projects.findById(projectNumber));
    }

    @Override
    public void delete(Long projectNumber) {
        projects.delete(projectNumber);
    }

    @Override
    public ProjectResponse update(UpdateProjectRequest req) {
        Project project = projects.findById(req.getProjectNumber());
        if(project != null){
            ProjectMapper.updateEntity(req, project);
            projects.save(project);
            return ProjectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public ProjectResponse addEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projects.findById(projectNumber);
        if(project != null){
            Employee employee = employeeService.getRaw(employeeNumber);
            project.getEmployeeList().add(employee);
            employee.setAssigned(true);
            return ProjectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public ProjectResponse removeEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projects.findById(projectNumber);
        if(project != null){
            Employee employee = employeeService.getRaw(employeeNumber);
            project.getEmployeeList().remove(employee);
            employee.setAssigned(false);
            return ProjectMapper.toResponse(project);
        }
        return null;
    }

    @Override
    public List<EmployeeResponse> getEmployeeList(Long projectNumber){
        Project project = projects.findById(projectNumber);
        if(project != null){
            return EmployeeMapper.toResponseList(project.getEmployeeList());
        }
        return null;
    }

    @Override
    public Long getProjectCount(){
        return projects.count();
    }

    @Override
    public Integer getEmployeeCount(Long projectNumber){
        return projects.countEmployeesInProject(projectNumber);
    }

    @Override
    public boolean existsProject(Long projectNumber){
        return projects.exists(projectNumber);
    }

}
