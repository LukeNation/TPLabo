package org.universidad.palermo.dto.response;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long employeeNumber;
    private String name;
    private Double salary;
    private Boolean assigned;
}
