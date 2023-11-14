package org.universidad.palermo.dao;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dao.interfaces.ITaskDao;
import org.universidad.palermo.entities.Task;
import org.universidad.palermo.mappers.TaskMapper;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
public class TaskDao implements ITaskDao {

    private final TaskMapper taskMapper;
    @Override
    public Task findById(Long idEntity) {
        return (Task) DaoBase.findById(idEntity,"Task", "task_number", taskMapper);
    }

    @Override
    public List<Task> findAll() {
        return (List<Task>) DaoBase.findAll("Task", taskMapper);
    }

    @Override
    public Task save(Task entity) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("INSERT INTO TASK(title, description, status, estimated_time, worked_time, project_number, assigned_employee) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getStatus());
            ps.setDouble(4, entity.getEstimatedHours());
            ps.setDouble(5, entity.getWorkedHours());
            ps.setLong(6, entity.getProject());
            ps.setLong(7, entity.getAssignedEmployee());
            int i = ps.executeUpdate();
            System.out.println("Registros insertados: " + i);
            if(i>0) {
                Long id = DaoBase.getLastId("task", "task_number");
                entity.setTaskNumber(id);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return entity;
    }

    @Override
    public Task update(Task entity) {
        PreparedStatement ps = null;
        try {
            ps = DaoBase.startConection("UPDATE task SET title = ?, description = ?, status = ?, estimated_time = ?, worked_time = ?, project_number = ?, assigned_employee = ? WHERE task_number = ?");
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getStatus());
            ps.setDouble(4, entity.getEstimatedHours());
            ps.setDouble(5, entity.getWorkedHours());
            ps.setLong(6, entity.getProject());
            ps.setLong(7, entity.getAssignedEmployee());
            ps.setLong(8, entity.getTaskNumber());
            int i = ps.executeUpdate();
            System.out.println("Registros actualizados: " + i);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return entity;
    }

    @Override
    public Boolean delete(Long idEntity) {
        return DaoBase.delete(idEntity,"task","task_number");
    }

    @Override
    public Long count() {
        return DaoBase.count("task");
    }

    @Override
    public Boolean exists(Long idEntity) {
        return DaoBase.exists(idEntity,"task","task_number");
    }

    @Override
    public List<Task> findAllByEmployee(Long employeeId) {
        return (List<Task>) DaoBase.findBy("task", "assigned_employee", employeeId, taskMapper);
    }

    @Override
    public List<Task> findAllByProject(Long projectId) {
        return (List<Task>) DaoBase.findBy("task", "project_number", projectId, taskMapper);
    }
}
