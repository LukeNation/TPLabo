package service;

import org.junit.jupiter.api.*;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.service.interfaces.EmployeeService;
import util.TestMasterProjectManager;

import java.util.Objects;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp(){
        TestMasterProjectManager tm = new TestMasterProjectManager();
        employeeService = tm.getEmployeeService();
    }

    @Nested
    class CreateTests {

        @Test
        @DisplayName("create: ok")
        public void create_ok() {
            Long employeeCount = employeeService.getEmployeeCount();
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == employeeCount+1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
            employeeService.delete(e1.getEmployeeNumber());
        }
    }

    @Nested
    class UpdateTests{

        @Test
        @DisplayName("update: ok")
        public void updateOk(){
            Long employeCoount = employeeService.getEmployeeCount();
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == employeCoount+1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
            UpdateEmployeeRequest request2 = new UpdateEmployeeRequest();
            request2.setEmployeeNumber(e1.getEmployeeNumber());
            request2.setName("name2");
            request2.setLastName("lastName2");
            request2.setSalary(2000.0);
            EmployeeResponse e2 = employeeService.update(request2);
            assert employeeService.getEmployeeCount() == employeCoount+1;
            assert employeeService.existsEmployee(e2.getEmployeeNumber());
            employeeService.delete(e1.getEmployeeNumber());
        }
    }

    @Nested
    class deleteTests{

        @Test
        @DisplayName("delete: ok")
        public void deleteOk(){
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            Long employeCoount = employeeService.getEmployeeCount();
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == employeCoount + 1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
            employeeService.delete(e1.getEmployeeNumber());
            assert Objects.equals(employeeService.getEmployeeCount(), employeCoount);
            assert !employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()) == null;
        }
    }

    @Nested
    class getTests{

    }
}
