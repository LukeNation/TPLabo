package org.universidad.palermo.dto.request;

import lombok.Data;

@Data
public class UpdateProjectRequest {

    private Long projectNumber;
    private String title;
    private String description;
}
