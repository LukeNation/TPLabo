package org.universidad.palermo.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Employee {

    private Long EmployeeNumber;
    @ToString.Exclude
    private String name;
    @ToString.Exclude
    private String lastName;
    private Double salary;
    private Boolean assigned;

    @ToString.Include(name = "Full name = ")
    public String getFullName() {
        return name + " " + lastName;
    }
}
