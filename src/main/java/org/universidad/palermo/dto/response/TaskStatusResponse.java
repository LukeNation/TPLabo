package org.universidad.palermo.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class TaskStatusResponse {
    private String status;
    private String description;
    private Date changeDate;
    private EmployeeResponse employee;
}
