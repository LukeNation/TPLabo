package org.universidad.palermo.dto.response;

import lombok.Data;

@Data
public class TaskResponse {

    private Long taskNumber;
    private String title;
    private String description;
    private String status;
    private Double estimatedHours;
    private Double workedHours;
    private EmployeeResponse assignedEmployee;
    private ProjectResponse project;
}
