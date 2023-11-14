package org.universidad.palermo.mappers;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.entities.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectMapper implements Mapper<Project>{

    final EmployeeMapper employeeMapper;
    final TaskMapper taskMapper;

    public Project toEntity(CreateProjectRequest request) {
        return new Project(request.getProjectNumber(),request.getTitle(),request.getDescription());
    }

    public void updateEntity(UpdateProjectRequest req, Project project) {
        project.setTitle(req.getTitle());
        project.setDescription(req.getDescription());
        project.setStatus(req.getStatus());
    }

    public ProjectResponse toResponse(Project project){
        ProjectResponse response = null;
        if(project != null) {
            response = new ProjectResponse();
            response.setProjectNumber(project.getProjectNumber());
            response.setTitle(project.getTitle());
            response.setDescription(project.getDescription());
            response.setStatus(project.getStatus());
            response.setEmployeeList(employeeMapper.toResponseList(project.getEmployeeList()));
            response.setTaskList(taskMapper.toResponseList(project.getTaskList()));
        }
        return response;

    }

    public List<ProjectResponse> toResponseList(List<Project> list) {
        return list.stream().map(this::toResponse).toList();
    }

    public Project toEntity(ResultSet rs) throws SQLException {
        Project project = null;
        if(rs.next()) {
            Long nro = rs.getLong("project_number");
            String title = rs.getString("title");
            String description = rs.getString("description");
            project = new Project(nro,title,description);
            project.setStatus(rs.getInt("status"));
        }
        return project;
    }

    public List<Project> toEntityList(ResultSet rs) throws SQLException {
        List<Project> projectList = new ArrayList<>();
        while (rs.next()){
            Long nro = rs.getLong("project_number");
            String title = rs.getString("title");
            String description = rs.getString("description");
            Project project = new Project(nro,title,description);
            project.setStatus(rs.getInt("status"));
            projectList.add(project);
        }
        return  projectList;
    }
}
