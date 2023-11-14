package org.universidad.palermo.dto.request;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private Long taskNumber;
    private String title;
    private String description;
    private Double estimatedHours;
    private Double workedHours;
    private Integer status;
}
