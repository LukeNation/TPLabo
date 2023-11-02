package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.universidad.palermo.dto.request.CreateEmployeeRequest;
import org.universidad.palermo.dto.request.UpdateEmployeeRequest;
import org.universidad.palermo.dto.response.EmployeeResponse;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.service.interfaces.EmployeeService;
import org.universidad.palermo.util.MasterProjectManager;
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
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == 1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
        }
    }

    @Nested
    class UpdateTests{

        @Test
        @DisplayName("update: ok")
        public void updateOk(){
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == 1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
            UpdateEmployeeRequest request2 = new UpdateEmployeeRequest();
            request2.setEmployeeNumber(e1.getEmployeeNumber());
            request2.setName("name2");
            request2.setLastName("lastName2");
            request2.setSalary(2000.0);
            EmployeeResponse e2 = employeeService.update(request2);
            assert employeeService.getEmployeeCount() == 1;
            assert employeeService.existsEmployee(e2.getEmployeeNumber());
            assert employeeService.get(e2.getEmployeeNumber()).equals(e1);
            assert Objects.equals(e2.getName(), "name2 lastName2");
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
            EmployeeResponse e1 = employeeService.create(request);
            assert employeeService.getEmployeeCount() == 1;
            assert employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()).equals(e1);
            employeeService.delete(e1.getEmployeeNumber());
            assert employeeService.getEmployeeCount() == 0;
            assert !employeeService.existsEmployee(e1.getEmployeeNumber());
            assert employeeService.get(e1.getEmployeeNumber()) == null;
        }
    }

    @Nested
    class getTests{

        @Test
        @DisplayName("get: ok")
        public void getOk(){
            CreateEmployeeRequest request = new CreateEmployeeRequest();
            request.setName("name");
            request.setLastName("lastName");
            request.setSalary(1000.0);
            EmployeeResponse e1 = employeeService.create(request);
            request.setName("name2");
            request.setLastName("lastName2");
            request.setSalary(2000.0);
            EmployeeResponse e2 = employeeService.create(request);
            assert employeeService.getAll().size() == 2;
            assert employeeService.getAll(false).size() == 2;
            assert employeeService.getAll(true).isEmpty();
            e2.setAssigned(true);
            assert employeeService.getAll(true).size() == 1;
            assert employeeService.getAll(false).size() == 1;
            assert employeeService.getAll().size() == 2;
        }
    }
}
