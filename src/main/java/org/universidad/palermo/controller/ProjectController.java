package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.service.interfaces.ProjectService;

import java.util.List;

@RequiredArgsConstructor
public class ProjectController implements Controller{

    private final ProjectService projectService;

    public ProjectResponse createProject(CreateProjectRequest req) {
        return projectService.create(req);
    }

    public ProjectResponse updateProject(UpdateProjectRequest req) {
       return projectService.update(req);
    }

    public void delete(Long projectNumber) {
        projectService.delete(projectNumber);
    }

    public ProjectResponse getProject(Long projectNumber) {
        return projectService.get(projectNumber);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectService.getAll();
    }

    public ProjectResponse addEmployee(Long projectNumber, Long employeeNumber) {
        return projectService.addEmployee(projectNumber, employeeNumber);
    }

    public ProjectResponse removeEmployee(Long projectNumber, Long employeeNumber) {
        return projectService.removeEmployee(projectNumber, employeeNumber);
    }

    public List<EmployeeResponse> getEmployees(Long projectNumber) {
       return projectService.getEmployeeList(projectNumber);
    }

    public TaskResponse createTask(Long projectNumber, CreateTaskRequest request) {
        return projectService.createTask(projectNumber, request);
    }

    public List<TaskResponse> getTasks(Long projectNumber) {
        return projectService.getTasks(projectNumber);
    }

}
