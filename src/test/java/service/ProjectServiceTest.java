package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.CreateProjectRequest;
import org.universidad.palermo.dto.request.UpdateProjectRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.dto.response.ProjectResponse;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.service.interfaces.ProjectService;
import util.TestMasterProjectManager;

import java.util.Objects;

public class ProjectServiceTest {

    private ProjectService projectService;
    private EmployeeService employeeSerivce;

    @BeforeEach
    public void setUp(){
        TestMasterProjectManager tm = new TestMasterProjectManager();
        projectService = tm.getProjectService();
        employeeSerivce = tm.getEmployeeService();
    }

    @Nested
    class CreateTests{

        @Test
        public void create_ok(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == projectCount+1;
            projectService.delete(p1.getProjectNumber());
        }
    }

    @Nested
    class UpdateTests{

        @Test
        public void update_ok(){
            Long projectCount = projectService.getProjectCount();
            ProjectResponse p1 = projectService.get(-1L);
            assert p1.getTitle().equals("TEST PROJECT");
            assert p1.getDescription().equals("UNVALID PROJECT");
            UpdateProjectRequest updateRequest = new UpdateProjectRequest();
            updateRequest.setProjectNumber(-1L);
            updateRequest.setTitle("title2");
            updateRequest.setDescription("description2");
            ProjectResponse p2 = projectService.update(updateRequest);
            assert p2.getTitle().equals("title2");
            assert p2.getDescription().equals("description2");
            assert Objects.equals(projectService.getProjectCount(), projectCount);
            updateRequest.setTitle("TEST PROJECT");
            updateRequest.setDescription("UNVALID PROJECT");
            projectService.update(updateRequest);
        }
    }

    @Nested
    class DeleteTests{
        @Test
        public void delete_ok(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == projectCount+1;
            projectService.delete(p1.getProjectNumber());
            assert Objects.equals(projectService.getProjectCount(), projectCount);
        }
    }

    @Nested
    class GetTests{
        @Test
        public void get_ok(){
            ProjectResponse p2 = projectService.get(-1L);
            assert p2.getTitle().equals("TEST PROJECT");
        }

        @Test
        public void get_notFound(){
            ProjectResponse p2 = projectService.get(-2L);
            assert p2 == null;
        }

        @Test
        public void getAll(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getProjectCount() == projectCount+1;
            CreateProjectRequest request2 = new CreateProjectRequest();
            request2.setTitle("title2");
            request2.setDescription("description2");
            ProjectResponse p2 = projectService.create(request2);
            assert projectService.getProjectCount() == projectCount+2;
            assert projectService.getAll().size() == projectCount+2;
            projectService.delete(p1.getProjectNumber());
            projectService.delete(p2.getProjectNumber());
        }

        @Test
        public void existsOk(){
            assert projectService.existsProject(-1L);
        }

        @Test
        public void existsNotOk(){
            assert !projectService.existsProject(-2L);
        }

    }

    @Nested
    class AddEmployeeTests{
        @Test
        public void addEmployee_ok(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            CreateEmployeeRequest request2 = new CreateEmployeeRequest();
            request2.setName("name");
            request2.setLastName("lastName");
            request2.setSalary(1000d);
            EmployeeResponse e1 = employeeSerivce.create(request2);
            assert projectService.getProjectCount() == projectCount+1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 1;
            projectService.removeEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            projectService.delete(p1.getProjectNumber());
            employeeSerivce.delete(e1.getEmployeeNumber());
        }

        @Test
        public void addEmployee_notFound(){
            assert projectService.addEmployee(-2L, 1L)==null;
        }

        @Test
        public void removeEmployee_ok(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            CreateEmployeeRequest request2 = new CreateEmployeeRequest();
            request2.setName("name");
            request2.setLastName("lastName");
            request2.setSalary(1000d);
            EmployeeResponse e1 = employeeSerivce.create(request2);
            assert projectService.getProjectCount() == projectCount + 1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            projectService.removeEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.removeEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            projectService.delete(p1.getProjectNumber());
            employeeSerivce.delete(e1.getEmployeeNumber());
        }

        @Test
        public void removeEmployee_notFound(){
            assert projectService.removeEmployee(-2L, 1L)== null;
        }

        @Test
        public void getEmployeeList(){
            Long projectCount = projectService.getProjectCount();
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            CreateEmployeeRequest request2 = new CreateEmployeeRequest();
            request2.setName("name");
            request2.setLastName("lastName");
            request2.setSalary(1000d);
            EmployeeResponse e1 = employeeSerivce.create(request2);

            assert projectService.getProjectCount() == projectCount+1;
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            projectService.addEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 1;
            assert projectService.getEmployeeList(p1.getProjectNumber()).size() == 1;
            projectService.removeEmployee(p1.getProjectNumber(), e1.getEmployeeNumber());
            projectService.delete(p1.getProjectNumber());
            employeeSerivce.delete(e1.getEmployeeNumber());
        }

        @Test
        public void getEmployeeList_notFound(){
            assert projectService.getEmployeeList(-2L) == null;
        }

        @Test
        public void getEmployeeList_empty(){
            CreateProjectRequest request = new CreateProjectRequest();
            request.setTitle("title");
            request.setDescription("description");
            ProjectResponse p1 = projectService.create(request);
            assert projectService.getEmployeeCount(p1.getProjectNumber()) == 0;
            assert projectService.getEmployeeList(p1.getProjectNumber()).isEmpty();
            projectService.delete(p1.getProjectNumber());
        }
    }
}
