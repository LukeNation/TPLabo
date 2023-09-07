package org.universidad.palermo.service;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.mappers.ProjectMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final EmployeeService employeeService;

    private static Long projectNumber = 0L;
    private static final Map<Long, Project> projects = new HashMap<>();

    @Override
    public String create(CreateProjectRequest req) {
        req.setProjectNumber(++projectNumber);
        Project project = ProjectMapper.toEntity(req);
        projects.put(projectNumber, project);
        return "Project created successfully";
    }

    @Override
    public List<Project> getAll() {
        return projects.values().stream().toList();
    }

    @Override
    public String get(Long projectNumber) {
        return projects.get(projectNumber).toString();
    }

    @Override
    public void delete(Long projectNumber) {
        projects.remove(projectNumber);
    }

    @Override
    public String update(UpdateProjectRequest req) {
        Project project = projects.get(req.getProjectNumber());
        if(project != null){
            ProjectMapper.updateEntity(req, project);
            return "Project updated successfully";
        }
        return "Project not found";
    }

    @Override
    public String addEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projects.get(projectNumber);
        if(project != null){
            Employee employee = employeeService.get(employeeNumber);
            project.getEmployeeList().add(employee);
            employee.setAssigned(true);
            return "Employee added successfully";
        }
        return "Project not found";
    }

    @Override
    public String removeEmployee(Long projectNumber, Long employeeNumber) {
        Project project = projects.get(projectNumber);
        if(project != null){
            Employee employee = employeeService.get(employeeNumber);
            project.getEmployeeList().remove(employee);
            employee.setAssigned(false);
            return "Employee removed successfully";
        }
        return "Project not found";
    }
}
