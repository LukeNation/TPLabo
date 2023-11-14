package org.universidad.palermo.service;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.TaskStatusDao;
import org.universidad.palermo.dao.interfaces.ITaskDao;
import org.universidad.palermo.dao.interfaces.ITaskStatusDao;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.dto.response.TaskStatusResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.entities.TaskStatus;
import org.universidad.palermo.enums.TaskStatusEnum;
import org.universidad.palermo.mappers.TaskMapper;
import org.universidad.palermo.mappers.TaskStatusMapper;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.TaskService;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final EmployeeService employeeService;
    private final ITaskDao tasksDao;
    private final ITaskStatusDao tasksStatusDao;

    private final TaskMapper taskMapper;
    private final TaskStatusMapper taskStatusMapper;

    @Override
    public TaskResponse createTask(CreateTaskRequest req) {
        Task task = taskMapper.toEntity(req);
        tasksDao.save(task);
        System.out.println("Task created successfully");
        updateTaskStatus(task.getTaskNumber(), TaskStatusEnum.getStatus(task.getStatus()), "tarea creada");
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse UpdateTask(UpdateTaskRequest req) {
        Task task = tasksDao.findById(req.getTaskNumber());
        if(task != null) {
            taskMapper.updateEntity(task, req);
            tasksDao.update(task);
            updateTaskStatus(req.getTaskNumber(), TaskStatusEnum.getStatus(task.getStatus()), "tarea actualizada");
            System.out.println("Task updated successfully");
            return taskMapper.toResponse(task);
        }
        System.out.println("Task not found");
        return null;
    }

    @Override
    public void deleteTask(Long taskNumber) {
        updateTaskStatus(taskNumber, TaskStatusEnum.DELETED, "tarea eliminada");
        tasksDao.delete(taskNumber);
    }

    @Override
    public TaskResponse GetTask(Long taskNumber) {
        return taskMapper.toResponse(tasksDao.findById(taskNumber));
    }

    @Override
    public List<TaskResponse> GetAllTasks() {
        return taskMapper.toResponseList(tasksDao.findAll());
    }

    @Override
    public List<TaskResponse> GetAllTasksByEmployee(Long employeeId) {
        List<Task> taskList = tasksDao.findAllByEmployee(employeeId);
        return taskMapper.toResponseList(taskList);
    }

    @Override
    public Long getTaskCount() {
        return tasksDao.count();
    }

    @Override
    public Boolean existsTask(Long taskNumber) {
        return tasksDao.exists(taskNumber);
    }

    @Override
    public TaskResponse assignTask(Long taskNumber, Long employeeNumber) {
        Task task = tasksDao.findById(taskNumber);
        if(task != null){
            task.setAssignedEmployee(employeeNumber);
            tasksDao.update(task);
            updateTaskStatus(taskNumber, TaskStatusEnum.getStatus(task.getStatus()), "tarea asignada");
            return taskMapper.toResponse(task);
        }
        return null;
    }

    @Override
    public List<TaskResponse> getTaskByProject(Long projectNumber) {
        return taskMapper.toResponseList(tasksDao.findAllByProject(projectNumber));
    }

    @Override
    public TaskStatusResponse updateTaskStatus(Long taskNumber, TaskStatusEnum status, String message) {
        Task task = tasksDao.findById(taskNumber);
        if(task != null){
            task.setStatus(status.getStatus());
            tasksDao.update(task);
            TaskStatus taskStatus = taskStatusMapper.toHistoric(task, message);
            tasksStatusDao.save(taskStatus);
            return taskStatusMapper.toResponse(taskStatus,employeeService.get(taskStatus.getEmployee()));
        }
        return null;
    }

    @Override
    public List<TaskStatusResponse> getHistoric(Long taskNumber) {
        List<TaskStatus>taskStatusList = tasksStatusDao.getHistoric(taskNumber);
        return taskStatusList.stream().map(taskStatus -> taskStatusMapper.toResponse(taskStatus,employeeService.get(taskStatus.getEmployee()))).sorted((Comparator.comparing(TaskStatusResponse::getChangeDate))).toList();
    }
}
