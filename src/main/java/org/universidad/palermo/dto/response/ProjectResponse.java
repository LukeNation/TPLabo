package org.universidad.palermo.dto.response;

import lombok.Data;
import org.universidad.palermo.entities.Employee;
import org.universidad.palermo.entities.Task;

import java.util.List;

@Data
public class ProjectResponse {

    private Long projectNumber;
    private String title;
    private String description;
    private Integer status;
    private List<EmployeeResponse> employeeList;
    private List<TaskResponse> taskList;

}
