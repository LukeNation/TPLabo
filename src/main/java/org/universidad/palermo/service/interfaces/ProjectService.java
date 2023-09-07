package org.universidad.palermo.service.interfaces;

import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.entities.Project;

import java.util.List;

public interface ProjectService {
    String create(CreateProjectRequest req);

    List<Project> getAll();

    String get(Long projectNumber);

    void delete(Long projectNumber);

    String update(UpdateProjectRequest req);

    String addEmployee(Long projectNumber, Long employeeNumber);

    String removeEmployee(Long projectNumber, Long employeeNumber);
}
