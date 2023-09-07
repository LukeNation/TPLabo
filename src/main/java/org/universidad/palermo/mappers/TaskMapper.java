package org.universidad.palermo.mappers;

import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.enums.ProjectStatus;

public class TaskMapper {

    public static Task toEntity(Long taskNumber, CreateTaskRequest req) {
        Task task = new Task();
        task.setTaskNumber(taskNumber);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours());
        task.setStatus(ProjectStatus.PENDING.getStatus());
        task.setWorkedHours(0D);
        return task;
    }

    public static void updateEntity(Task task, UpdateTaskRequest req) {
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setEstimatedHours(req.getEstimatedHours());
        task.setWorkedHours(req.getWorkedHours());
    }
}
