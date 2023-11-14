package org.universidad.palermo.enums;

import lombok.Getter;

@Getter
public enum TaskStatusEnum {

    PENDING(0,"pendiente"),
    BLOCKED(1,"Bloqueado"),
    IN_PROGRESS(2,"en Progreso"),
    FINISHED(3,"Finalizado"),
    REJECTED(4,"Rechazado"),
    DELETED(5,"Eliminada");

    private final int status;
    private final String description;

    TaskStatusEnum(int status, String description){
        this.status = status;
        this.description = description;
    }

    public static TaskStatusEnum getStatus(int status){
        return TaskStatusEnum.values()[status];
    }
}
