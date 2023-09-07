package org.universidad.palermo.dto.request;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {

    private Long EmployeeNumber;
    private String name;
    private String lastName;
    private Double salary;
}
