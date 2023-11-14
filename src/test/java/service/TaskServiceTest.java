package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.service.interfaces.TaskService;
import util.TestMasterProjectManager;

import java.util.Objects;

public class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    public void setUp(){
        TestMasterProjectManager tm = new TestMasterProjectManager();
        taskService = tm.getTaskService();
    }

    private CreateTaskRequest createTaskRequest(){
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setEstimatedHours(10D);
        request.setProjectNumber(-1L);
        request.setEmployeeNumber(-1L);
        return request;
    }

    @Nested
    class CreateTests{

        @Test
        public void create_ok(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count + 1;
            taskService.deleteTask(t1.getTaskNumber());
        }
    }

    @Nested
    class UpdateTests{

        @Test
        public void update_ok(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count+1;
            UpdateTaskRequest updateRequest = new UpdateTaskRequest();
            updateRequest.setTaskNumber(t1.getTaskNumber());
            updateRequest.setTitle("title2");
            updateRequest.setDescription("description2");
            updateRequest.setEstimatedHours(20D);
            TaskResponse t2 = taskService.UpdateTask(updateRequest);
            assert taskService.getTaskCount() == count+1;
            assert t2.getTitle().equals("title2");
            assert t2.getDescription().equals("description2");
            assert t2.getEstimatedHours().equals(20D);
            taskService.deleteTask(t1.getTaskNumber());
        }

        @Test
        public void update_notFound(){
            UpdateTaskRequest updateRequest = new UpdateTaskRequest();
            updateRequest.setTaskNumber(1L);
            updateRequest.setTitle("title2");
            updateRequest.setDescription("description2");
            updateRequest.setEstimatedHours(20D);
            TaskResponse t2 = taskService.UpdateTask(updateRequest);
            assert t2 == null;
        }
    }

    @Nested
    class DeleteTests{

        @Test
        public void delete_ok(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count+1;
            assert taskService.existsTask(t1.getTaskNumber());
            taskService.deleteTask(t1.getTaskNumber());
            assert !taskService.existsTask(t1.getTaskNumber());
            assert Objects.equals(taskService.getTaskCount(), count);
        }
    }

    @Nested
    class GetTests{

        @Test
        public void get_ok(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count+1;
            TaskResponse t2 = taskService.GetTask(t1.getTaskNumber());
            assert t1.getDescription().equals(t2.getDescription());
            taskService.deleteTask(t1.getTaskNumber());
        }

        @Test
        public void get_notFound(){
            TaskResponse t1 = taskService.GetTask(-1L);
            assert t1 == null;
        }

        @Test
        public void get_All(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count+1;
            assert taskService.GetAllTasks().size() == count+1;
            taskService.deleteTask(t1.getTaskNumber());
        }

        @Test
        public void get_AllByEmployee(){
            Long count = taskService.getTaskCount();
            CreateTaskRequest request = createTaskRequest();
            TaskResponse t1 = taskService.createTask(request);
            assert taskService.getTaskCount() == count+1;
            taskService.assignTask(t1.getTaskNumber(), -1L);
            assert taskService.GetAllTasksByEmployee(-1L).size() == 1;
            taskService.deleteTask(t1.getTaskNumber());
        }

        @Test
        public void assignTask_fail(){
            TaskResponse t2 = taskService.assignTask(-1L, -1L);
            assert t2 == null;
        }

    }
}
