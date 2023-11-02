package org.universidad.palermo.dto.request;

import lombok.Data;

@Data
public class CreateTaskRequest {

    private Long taskNumber;
    private String title;
    private String description;
    private Double estimatedHours;
}
