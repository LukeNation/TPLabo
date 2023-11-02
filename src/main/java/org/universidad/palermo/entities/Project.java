package org.universidad.palermo.entities;

import lombok.Data;
import org.universidad.palermo.enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class Project {

        private Long projectNumber;
        private String title;
        private String description;
        private Integer status;
        private List<Employee> employeeList;
        private List<Task> taskList;

        public Project(Long projectNumber, String title, String description) {
                this.projectNumber = projectNumber;
                this.title = title;
                this.description = description;
                this.status = ProjectStatus.PENDING.getStatus();
                this.employeeList = new ArrayList<>();
                this.taskList = new ArrayList<>();
        }

        public Boolean employeeAssigned(Long employeeNumber){
                return employeeList.stream().anyMatch(e -> e.getEmployeeNumber().equals(employeeNumber));
        }
}
