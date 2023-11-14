package org.universidad.palermo.entities;

import lombok.Data;

@Data
public class Task implements Entity{

    private Long taskNumber;
    private String title;
    private String description;
    private Integer status;
    private Double estimatedHours;
    private Double workedHours;
    private Long assignedEmployee;
    private Long project;
}
