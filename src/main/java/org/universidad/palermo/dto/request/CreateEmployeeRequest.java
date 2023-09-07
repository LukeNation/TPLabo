package org.universidad.palermo.dto.request;

import lombok.Data;

@Data
public class CreateEmployeeRequest {

    private String name;
    private String lastName;
    private Double salary;
}
