package org.universidad.palermo.controller;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.service.interfaces.ProjectService;

@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    public void createProject(CreateProjectRequest req) {
        System.out.println(projectService.create(req));
    }

    public void updateProject(UpdateProjectRequest req) {
        System.out.println(projectService.update(req));
    }

    public void deleteProject(Long projectNumber) {
        projectService.delete(projectNumber);
    }

    public void getProject(Long projectNumber) {
        System.out.println(projectService.get(projectNumber));
    }

    public void getAllProjects() {
        projectService.getAll().forEach(System.out::println);
    }

    public void addEmployee(Long projectNumber, Long employeeNumber) {
        System.out.println(projectService.addEmployee(projectNumber, employeeNumber));
    }

    public void removeEmployee(Long projectNumber, Long employeeNumber) {
        System.out.println(projectService.removeEmployee(projectNumber, employeeNumber));
    }


}
