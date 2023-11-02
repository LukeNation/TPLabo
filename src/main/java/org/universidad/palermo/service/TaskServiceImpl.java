package org.universidad.palermo.service;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.mappers.TaskMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final EmployeeService employeeService;
    private Long taskNumber = 0L;
    private final Map<Long, Task> tasks = new HashMap<>();

    @Override
    public TaskResponse CreateTask(CreateTaskRequest req) {
        Task task = TaskMapper.toEntity(++taskNumber,req);
        tasks.put(taskNumber, task);
        System.out.println("Task created successfully");
        return TaskMapper.toResponse(task);
    }

    @Override
    public TaskResponse UpdateTask(UpdateTaskRequest req) {
        Task task = tasks.get(req.getTaskNumber());
        if(task != null) {
            TaskMapper.updateEntity(task, req);
            System.out.println("Task updated successfully");
            return TaskMapper.toResponse(task);
        }
        System.out.println("Task not found");
        return null;
    }

    @Override
    public void DeleteTask(Long taskNumber) {
        tasks.remove(taskNumber);
    }

    @Override
    public TaskResponse GetTask(Long taskNumber) {
        return TaskMapper.toResponse(tasks.get(taskNumber));
    }

    @Override
    public List<TaskResponse> GetAllTasks() {
        return TaskMapper.toResponseList(tasks.values().stream().toList());
    }

    @Override
    public List<TaskResponse> GetAllTasksByEmployee(Long employeeId) {
        List<Task> taskList = tasks.values().stream().filter(task -> task.getAssignedEmployee().getEmployeeNumber().equals(employeeId)).collect(Collectors.toList());
        return TaskMapper.toResponseList(taskList);
    }

    @Override
    public int getTaskCount() {
        return tasks.size();
    }

    @Override
    public Boolean existsTask(Long taskNumber) {
        return tasks.containsKey(taskNumber);
    }

    @Override
    public TaskResponse assignTask(Long taskNumber, Long employeeNumber) {
        Task task = tasks.get(taskNumber);
        if(task != null){
            task.setAssignedEmployee(employeeService.getRaw(employeeNumber));
            return TaskMapper.toResponse(task);
        }
        return null;
    }
}
