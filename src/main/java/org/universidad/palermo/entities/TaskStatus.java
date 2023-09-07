package org.universidad.palermo.entities;

import lombok.Data;
import org.universidad.palermo.enums.ProjectStatus;

import java.util.Date;

@Data
public class TaskStatus {

    private int status;
    private String description;
    private Date changeDate;
    private Employee employee;

}
