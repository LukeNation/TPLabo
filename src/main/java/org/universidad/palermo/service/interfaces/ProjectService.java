package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.entities.Project;

import java.util.List;

public interface ProjectService {
    ProjectResponse create(CreateProjectRequest req);

    List<ProjectResponse> getAll();

    ProjectResponse get(Long projectNumber);

    void delete(Long projectNumber);

    ProjectResponse update(UpdateProjectRequest req);

    ProjectResponse addEmployee(Long projectNumber, Long employeeNumber);

    ProjectResponse removeEmployee(Long projectNumber, Long employeeNumber);

    List<EmployeeResponse> getEmployeeList(Long projectNumber);

    Long getProjectCount();

    Integer getEmployeeCount(Long projectNumber);

    boolean existsProject(Long projectNumber);

}
