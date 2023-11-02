package org.universidad.palermo.dto.response;

import lombok.Data;
import org.universidad.palermo.entities.Employee;

@Data
public class TaskResponse {

    private Long taskNumber;
    private String title;
    private String description;
    private Integer status;
    private Double estimatedHours;
    private Double workedHours;
    private EmployeeResponse assignedEmployee;
}
