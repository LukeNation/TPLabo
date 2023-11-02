package org.universidad.palermo.dao;

import org.universidad.palermo.dao.interfaces.DaoBase;
import org.universidad.palermo.dao.interfaces.IProjectDao;
import org.universidad.palermo.entities.Project;
import org.universidad.palermo.mappers.ProjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDao implements IProjectDao {

    private PreparedStatement startConection(String query) throws Exception {
        Connection cn;
        PreparedStatement ps = null;
        try {
            cn = DaoBase.getConnection();
            ps = cn.prepareStatement(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ps != null){
            return ps;
        }else{
            throw new Exception("Error al crear la sentencia");
        }
    }

    private void endConnection(PreparedStatement ps){
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Long count() {
        PreparedStatement ps = null;
        Long counter = null;
        try {
            ps = startConection("SELECT COUNT(*) FROM project");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                counter = rs.getLong(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return counter;
    }

    public Integer countEmployeesInProject(Long idProject){
        PreparedStatement ps = null;
        Integer counter = null;
        try {
            ps = startConection("SELECT COUNT(*) FROM PROJECT_EMPLOYEE WHERE project_number = ?");
            ps.setLong(1,idProject);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                counter = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return counter;
    }

    @Override
    public Project findById(Long idEntity) {
        PreparedStatement ps = null;
        Project project = null;
        try {
            ps = startConection("SELECT * FROM project WHERE project_number = ?");
            ps.setLong(1,idEntity);
            ResultSet rs = ps.executeQuery();
            project = ProjectMapper.toEntity(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return project;
    }

    @Override
    public List<Project> findAll() {
        PreparedStatement ps = null;
        List<Project> projectList = null;
        try {
            ps = startConection("SELECT * FROM project");
            ResultSet rs = ps.executeQuery();
            projectList = ProjectMapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return projectList;
    }

    @Override
    public Project save(Project entity) {
        PreparedStatement ps = null;
        try{
            ps = startConection("INSERT INTO project VALUES(?,?,?)");
            ps.setString(1, entity.getTitle());
            ps.setString(2,entity.getDescription());
            ps.setInt(3,entity.getStatus());
            int i = ps.executeUpdate();
            System.out.println("Registros insertados: " + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            endConnection(ps);
        }
        return entity;
    }

    @Override
    public Boolean delete(Long idEntity) {
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = startConection("DELETE FROM project WHERE project_number = ?");
            ps.setLong(1,idEntity);
            int i = ps.executeUpdate();
            System.out.println("Registros eliminados: " + i);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return result;
    }

    @Override
    public Boolean exists(Long idEntity) {
        PreparedStatement ps = null;
        boolean result = false;
        try{
            ps = startConection("SELECT * FROM project WHERE project_number = ?");
            ps.setLong(1, idEntity);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return result;
    }
}
