package org.universidad.palermo.dao;

import lombok.RequiredArgsConstructor;
import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.IEmployeeDao;
import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.mappers.ProjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectDao implements IProjectDao {

    private final ProjectMapper projectMapper;
    private final IEmployeeDao employeeDao;

    @Override
    public Long count() {
        return DaoBase.count("project");
    }

    @Override
    public boolean includeEmployee(Long projectNumber, Long employeeNumber){
        PreparedStatement ps = null;
        boolean include = false;
        try {
            ps = DaoBase.startConection("INSERT INTO PROJECT_EMPLOYEE(project_number, employee_number) VALUES(?,?)");
            ps.setLong(1, projectNumber);
            ps.setLong(2, employeeNumber);
            int i = ps.executeUpdate();
            if(i>0) {
                include = true;
            }
            System.out.println("Registros insertados: " + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return include;
    }

    @Override
    public boolean removeEmployee(Long projectNumber, Long employeeNumber){
        PreparedStatement ps = null;
        boolean remove = false;
        try {
            ps = DaoBase.startConection("DELETE FROM PROJECT_EMPLOYEE WHERE project_number = ? AND employee_number = ?");
            ps.setLong(1, projectNumber);
            ps.setLong(2, employeeNumber);
            int i = ps.executeUpdate();
            if(i>0) {
                remove = true;
            }
            System.out.println("Registros insertados: " + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return remove;
    }

    public Integer countEmployeesInProject(Long idProject){
        PreparedStatement ps = null;
        Integer counter = null;
        try {
            ps = DaoBase.startConection("SELECT COUNT(*) FROM PROJECT_EMPLOYEE WHERE project_number = ?");
            ps.setLong(1,idProject);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                counter = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return counter;
    }

    @Override
    public Project findById(Long idEntity) {
        Project project = (Project) DaoBase.findById(idEntity,"project","project_number", projectMapper);
        List<Long> employees = findEmployeeList(idEntity);
        employees.forEach(employee -> project.getEmployeeList().add(employeeDao.findById(employee)));
        return project;
    }

    @Override
    public List<Long> findEmployeeList(Long idProject){
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Long> employeeList = new ArrayList<>();
        try {
            ps = DaoBase.startConection("SELECT employee_number FROM PROJECT_EMPLOYEE WHERE project_number = ?");
            ps.setLong(1,idProject);
            rs = ps.executeQuery();
            while(rs.next()){
                employeeList.add(rs.getLong(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DaoBase.endConnection(ps);
        }
        return employeeList;
    }

    @Override
    public List<Project> findAll() {
        return (List<Project>) DaoBase.findAll("project", projectMapper);
    }

    @Override
    public Project save(Project entity) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("INSERT INTO project(title, description, status) VALUES(?,?,?)");
            ps.setString(1, entity.getTitle());
            ps.setString(2,entity.getDescription());
            ps.setInt(3,entity.getStatus());
            int i = ps.executeUpdate();
            System.out.println("Registros insertados: " + i);
            if(i>0) {
                Long id = DaoBase.getLastId("project", "project_number");
                entity.setProjectNumber(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return entity;
    }

    @Override
    public Project update(Project entity) {
        PreparedStatement ps = null;
        try{
            ps = DaoBase.startConection("UPDATE project SET title = ?, description = ?, status = ? WHERE project_number = ?");
            ps.setString(1, entity.getTitle());
            ps.setString(2,entity.getDescription());
            ps.setInt(3,entity.getStatus());
            ps.setLong(4,entity.getProjectNumber());
            int i = ps.executeUpdate();
            System.out.println("Registros insertados: " + i);
            if(i>0) {
                Long id = DaoBase.getLastId("project", "project_number");
                entity.setProjectNumber(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoBase.endConnection(ps);
        }
        return entity;
    }

    @Override
    public Boolean delete(Long idEntity) {
        return DaoBase.delete(idEntity,"project","project_number");
    }

    @Override
    public Boolean exists(Long idEntity) {
        return DaoBase.exists(idEntity,"project","project_number");
    }
}
