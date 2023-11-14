package org.universidad.palermo.entities;

import lombok.Data;

import java.util.Date;

@Data
public class TaskStatus {

    private Long id;
    private Long taskNumber;
    private int status;
    private String description;
    private Date changeDate;
    private Long employee;

}
