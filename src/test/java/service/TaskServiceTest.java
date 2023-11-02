package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.universidad.palermo.dto.request.CreateTaskRequest;
import org.universidad.palermo.dto.request.UpdateTaskRequest;
import org.universidad.palermo.dto.response.TaskResponse;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.service.interfaces.TaskService;
import util.TestMasterProjectManager;

public class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    public void setUp(){
        TestMasterProjectManager tm = new TestMasterProjectManager();
        tm.poblateEmployeeServiceTest();
        taskService = tm.getTaskService();
    }

    @Nested
    class CreateTests{

        @Test
        public void create_ok(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
        }
    }

    @Nested
    class UpdateTests{

        @Test
        public void update_ok(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            UpdateTaskRequest updateRequest = new UpdateTaskRequest();
            updateRequest.setTaskNumber(1L);
            updateRequest.setTitle("title2");
            updateRequest.setDescription("description2");
            updateRequest.setEstimatedHours(20D);
            TaskResponse t2 = taskService.UpdateTask(updateRequest);
            assert taskService.getTaskCount() == 1;
            assert t2.getTitle().equals("title2");
            assert t2.getDescription().equals("description2");
            assert t2.getEstimatedHours().equals(20D);
            assert t1 == t2;
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
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            assert taskService.existsTask(1L);
            taskService.DeleteTask(1L);
            assert !taskService.existsTask(1L);
            assert taskService.getTaskCount() == 0;
        }
    }

    @Nested
    class GetTests{

        @Test
        public void get_ok(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            TaskResponse t2 = taskService.GetTask(1L);
            assert t1 == t2;
        }

        @Test
        public void get_notFound(){
            TaskResponse t1 = taskService.GetTask(1L);
            assert t1 == null;
        }

        @Test
        public void get_All(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            assert taskService.GetAllTasks().size() == 1;
        }

        @Test
        public void get_AllByEmployee(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            taskService.assignTask(1L, 1L);
            assert taskService.GetAllTasksByEmployee(1L).size() == 1;
        }

        @Test
        public void assignTask_fail(){
            CreateTaskRequest request = new CreateTaskRequest();
            request.setTitle("title");
            request.setDescription("description");
            request.setEstimatedHours(10D);
            TaskResponse t1 = taskService.CreateTask(request);
            assert taskService.getTaskCount() == 1;
            TaskResponse t2 = taskService.assignTask(2L, 2L);
            assert t2 == null;
        }

    }



}
