package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.service.interfaces.ProjectService;
import org.universidad.palermo.util.MasterProjectManager;
import util.TestMasterProjectManager;

public class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    public void setUp(){
        TestMasterProjectManager tm = new TestMasterProjectManager();
        tm.poblateEmployeeServiceTest();
        projectService = tm.getProjectService();
    }

    @Nested
    class CreateTests{

        @Test
        public void create_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
        }
    }

    @Nested
    class UpdateTests{

        @Test
        public void update_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert p1.getTitle().equals("title");
            UpdateProjectRequest updateRequest = new UpdateProjectRequest();
            updateRequest.setProjectNumber(p1.getProjectNumber());
            updateRequest.setTitle("title2");
            updateRequest.setDescription("description2");
            ProjectResponse p2 = projectService.update(updateRequest);
            assert p2.getTitle().equals("title2");
            assert p2.getDescription().equals("description2");
            assert p1 == p2;
            assert projectService.getProjectCount() == 1;
        }
    }

    @Nested
    class DeleteTests{
        @Test
        public void delete_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            projectService.delete(p1.getProjectNumber());
            assert projectService.getProjectCount() == 0;
        }
    }

    @Nested
    class GetTests{
        @Test
        public void get_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            ProjectResponse p2 = projectService.get(p1.getProjectNumber());
            assert p1 == p2;
        }

        @Test
        public void get_notFound(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            ProjectResponse p2 = projectService.get(2L);
            assert p2 == null;
        }

        @Test
        public void getAll(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            CreateProjectRequest request2 = new CreateProjectRequest();
            request2.setTitle("title2");
            request2.setDescription("description2");
            ProjectResponse p2 = projectService.create(request2);
            assert projectService.getProjectCount() == 2;
            assert projectService.getAll().size() == 2;
        }

        @Test
        public void existsOk(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert projectService.existsProject(p1.getProjectNumber());
        }

        @Test
        public void existsNotOk(){
            assert !projectService.existsProject(1L);
        }

    }

    @Nested
    class AddEmployeeTests{
        @Test
        public void addEmployee_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), 1L);
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 1;
        }

        @Test
        public void addEmployee_notFound(){
            assert projectService.addEmployee(1L, 1L).equals("Project not found");
        }

        @Test
        public void removeEmployee_ok(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), 1L);
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 1;
            projectService.removeEmployee(p1.getProjectNumber(), 1L);
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
        }

        @Test
        public void removeEmployee_notFound(){
            assert projectService.removeEmployee(1L, 1L).equals("Project not found");
        }

        @Test
        public void getEmployeeList(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), 1L);
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 1;
            assert projectService.getEmployeeList(p1.getProjectNumber()).size() == 1;
        }

        @Test
        public void getEmployeeList_notFound(){
            assert projectService.getEmployeeList(1L) == null;
        }

        @Test
        public void getEmployeeList_empty(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == 1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            assert projectService.getEmployeeList(p1.getProjectNumber()).isEmpty();
        }
    }
}
