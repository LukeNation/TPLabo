package org.universidad.palermo.dao;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.ITaskStatusDao;
import org.universidad.palermo.entities.TaskStatus;
import org.universidad.palermo.mappers.TaskStatusMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
public class TaskStatusDao implements ITaskStatusDao {

    private final TaskStatusMapper taskStatusMapper;

    @Override
    public TaskStatus findById(Long idEntity) {
        return (TaskStatus) DaoBase.findById(idEntity,"TASK_STATUS_HISTORIC", "id", taskStatusMapper);
    }

    @Override
    public List<TaskStatus> findAll() {
        return (List<TaskStatus>) DaoBase.findAll("TASK_STATUS_HISTORIC", taskStatusMapper);
    }

    @Override
    public TaskStatus save(TaskStatus entity) {
        PreparedStatement ps = null;
        try{
           ps = DaoBase.startConection("INSERT INTO TASK_STATUS_HISTORIC(task_number, status, descripcion, date, employee) VALUES(?,?,?,?,?)");
                ps.setLong(1, entity.getTaskNumber());
                ps.setInt(2, entity.getStatus());
                ps.setString(3, entity.getDescription());
                ps.setDate(4, new Date(entity.getChangeDate().getTime()));
                ps.setLong(5, entity.getEmployee());
                int i = ps.executeUpdate();
                System.out.println("Registros insertados: " + i);
                if(i>0) {
                    Long id = DaoBase.getLastId("task_status_historic", "id");
                    entity.setId(id);
                }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return entity;
    }

    @Override
    public TaskStatus update(TaskStatus entity) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("UPDATE TASK_STATUS_HISTORIC SET task_number = ?, status = ?, descripcion = ?, date = ?, employee = ? WHERE id = ?");
            ps.setLong(1, entity.getTaskNumber());
            ps.setInt(2, entity.getStatus());
            ps.setString(3, entity.getDescription());
            ps.setDate(4, new Date(entity.getChangeDate().getTime()));
            ps.setLong(5, entity.getEmployee());
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
        return DaoBase.delete(idEntity,"TASK_STATUS_HISTORIC","id");
    }

    @Override
    public Long count() {
        return DaoBase.count("TASK_STATUS_HISTORIC");
    }

    @Override
    public Boolean exists(Long idEntity) {
        return DaoBase.exists(idEntity,"TASK_STATUS_HISTORIC","id");
    }

    @Override
    public List<TaskStatus> getHistoric(Long taskNumber) {
        return (List<TaskStatus>) DaoBase.findBy("TASK_STATUS_HISTORIC", "task_number", taskNumber, taskStatusMapper);
    }
}
