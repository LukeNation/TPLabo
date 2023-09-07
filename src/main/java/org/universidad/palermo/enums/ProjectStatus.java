package org.universidad.palermo.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ProjectStatus {

    PENDING(0),BLOCKED(1),IN_PROGRESS(2),FINISHED(3),CANCELLED(4);

    private final int status;

    ProjectStatus(int status){
        this.status = status;
    }

}
