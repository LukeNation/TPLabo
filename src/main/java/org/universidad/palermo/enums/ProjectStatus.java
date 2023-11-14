package org.universidad.palermo.enums;

import lombok.Getter;

@Getter
public enum ProjectStatus {

    PENDING(0, "pendiente"),
    BLOCKED(1, "Bloqueado"),
    IN_PROGRESS(2, "en Progreso"),
    FINISHED(3, "Finalizado"),
    CANCELLED(4, "Cancelado");

    private final int status;
    private final String description;

    ProjectStatus(int status, String description){
        this.status = status;
        this.description = description;
    }

    public static ProjectStatus getStatus(int status){
        return ProjectStatus.values()[status];
    }
}
