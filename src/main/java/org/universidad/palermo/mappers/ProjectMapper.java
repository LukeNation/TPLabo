package org.universidad.palermo.mappers;

import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.entities.Project;

public class ProjectMapper {
    public static Project toEntity(CreateProjectRequest request) {
        return new Project(request.getProjectNumber(),request.getTitle(),request.getDescription());
    }

    public static void updateEntity(UpdateProjectRequest req, Project project) {
        project.setTitle(req.getTitle());
        project.setDescription(req.getDescription());
    }
}
