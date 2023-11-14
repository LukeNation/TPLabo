package org.universidad.palermo.mappers;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.TaskStatusResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.entities.TaskStatus;
import org.universidad.palermo.enums.TaskStatusEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskStatusMapper implements Mapper<TaskStatus>{

    @Override
    public List<TaskStatus> toEntityList(ResultSet rs) throws SQLException {
        List<TaskStatus> statusList = new ArrayList<>();
        while(rs.next()) {
            TaskStatus status = new TaskStatus();
            status.setId(rs.getLong("id"));
            status.setTaskNumber(rs.getLong("task_number"));
            status.setStatus(rs.getInt("status"));
            status.setDescription(rs.getString("descripcion"));
            status.setChangeDate(rs.getDate("date"));
            status.setEmployee(rs.getLong("employee"));
            statusList.add(status);
        }
        statusList.sort((o1, o2) -> o2.getChangeDate().compareTo(o1.getChangeDate()));
        return statusList;
    }

    @Override
    public TaskStatus toEntity(ResultSet rs) throws SQLException {
        TaskStatus status = null;
        if(rs.next()) {
            status = new TaskStatus();
            status.setId(rs.getLong("id"));
            status.setTaskNumber(rs.getLong("task_number"));
            status.setStatus(rs.getInt("status"));
            status.setDescription(rs.getString("description"));
            status.setChangeDate(rs.getDate("date"));
            status.setEmployee(rs.getLong("employee"));
        }
        return status;
    }

    public TaskStatusResponse toResponse(TaskStatus taskStatus, EmployeeResponse employeeResponse) {
        if(taskStatus != null) {
            TaskStatusResponse response = new TaskStatusResponse();
            response.setStatus(TaskStatusEnum.getStatus(taskStatus.getStatus()).getDescription());
            response.setDescription(taskStatus.getDescription());
            response.setChangeDate(taskStatus.getChangeDate());
            response.setEmployee(employeeResponse);
            return response;
        }
        return null;
    }
    public TaskStatusResponse toResponse(TaskStatus taskStatus) {
        if(taskStatus != null) {
            TaskStatusResponse response = new TaskStatusResponse();
            response.setStatus(TaskStatusEnum.getStatus(taskStatus.getStatus()).getDescription());
            response.setDescription(taskStatus.getDescription());
            response.setChangeDate(taskStatus.getChangeDate());
            return response;
        }
        return null;
    }

    public TaskStatus toHistoric(Task task, String message){
        if (task != null){
            TaskStatus status = new TaskStatus();
            status.setTaskNumber(task.getTaskNumber());
            status.setStatus(task.getStatus());
            status.setDescription(message);
            status.setChangeDate(new Date());
            status.setEmployee(task.getAssignedEmployee());
            return status;
        }
        return null;
    }

    public List<TaskStatusResponse> toResponseList(List<TaskStatus> taskList) {
        return taskList.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
