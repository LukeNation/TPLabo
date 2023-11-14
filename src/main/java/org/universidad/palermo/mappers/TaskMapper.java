package org.universidad.palermo.mappers;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.enums.ProjectStatus;
import org.universidad.palermo.enums.TaskStatusEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskMapper implements Mapper<Task> {

    private final EmployeeMapper employeeMapper;

    public Task toEntity(CreateTaskRequest req) {
        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours());
        task.setStatus(ProjectStatus.PENDING.getStatus());
        task.setProject(req.getProjectNumber());
        task.setAssignedEmployee(req.getEmployeeNumber());
        task.setWorkedHours(0D);
        return task;
    }

    public void updateEntity(Task task, UpdateTaskRequest req) {
        task.setTitle(req.getTitle()==null? task.getTitle() : req.getTitle());
        task.setDescription(req.getDescription()==null? task.getDescription() : req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours()==null? task.getEstimatedHours() :req.getEstimatedHours());
        task.setWorkedHours(req.getWorkedHours()==null? task.getWorkedHours() :req.getWorkedHours());
        task.setStatus(req.getStatus()==null? task.getStatus() :req.getStatus());
    }

    public TaskResponse toResponse(Task task) {
        if(task != null) {
            TaskResponse response = new TaskResponse();
            response.setTaskNumber(task.getTaskNumber());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setStatus(TaskStatusEnum.getStatus(task.getStatus()).getDescription());
            response.setEstimatedHours(task.getEstimatedHours());
            response.setWorkedHours(task.getWorkedHours());
            return response;
        }
        return null;
    }

    public List<TaskResponse> toResponseList(List<Task> taskList) {
        return taskList.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Task toEntity(ResultSet rs) throws SQLException {
        Task task = null;
        if(rs.next()) {
            task = new Task();
            task.setTaskNumber(rs.getLong("task_number"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setEstimatedHours(rs.getDouble("estimated_time"));
            task.setWorkedHours(rs.getDouble("worked_time"));
            task.setStatus(TaskStatusEnum.getStatus(rs.getInt("status")).getStatus());
            task.setAssignedEmployee(rs.getLong("assigned_employee"));
            task.setProject(rs.getLong("project_number"));
        }
        return task;
    }

    public List<Task> toEntityList(ResultSet rs) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        while (rs.next()){
            Task task = new Task();
            task.setTaskNumber(rs.getLong("task_number"));
            task.setTitle(rs.getString("title"));
            task.setWorkedHours(rs.getDouble("worked_time"));
            task.setStatus(TaskStatusEnum.getStatus(rs.getInt("status")).getStatus());
            task.setDescription(rs.getString("description"));
            task.setEstimatedHours(rs.getDouble("estimated_time"));
            task.setAssignedEmployee(rs.getLong("assigned_employee"));
            task.setProject(rs.getLong("project_number"));
            taskList.add(task);
        }
        return taskList;
    }
}
