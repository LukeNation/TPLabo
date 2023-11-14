package org.universidad.palermo.dao.interfaces;

import org.universidad.palermo.entities.Entity;
import org.universidad.palermo.mappers.EmployeeMapper;
import org.universidad.palermo.mappers.Mapper;
import org.universidad.palermo.mappers.ProjectMapper;
import org.universidad.palermo.mappers.TaskMapper;

import java.sql.*;
import java.util.List;
import java.util.Map;

public interface DaoBase<Type, ID>{

    String DB_URL = "jdbc:postgresql://localhost:2300/postgres";
    String DB_USER ="postgres";
    String DB_PASSWORD = "password";



    Type findById(ID idEntity);

    List<Type> findAll();

    Type save (Type entity);

    Type update (Type entity);

    Boolean delete (ID idEntity);

    Long count();

    Boolean exists(ID idEntity);

    static Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    static PreparedStatement startConection(String query) throws Exception {
        Connection cn;
        PreparedStatement ps = null;
        try {
            cn = getConnection();
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

    static void endConnection(PreparedStatement ps){
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static Long count(String tableName){
        PreparedStatement ps = null;
        Long counter = null;
        try {
            ps = startConection("SELECT COUNT(*) FROM "+tableName);
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

    static Entity  findById(Long idEntity, String tableName, String idColumn, Mapper<?> mapper) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Entity entity = null;
        try {
            ps = startConection("SELECT * FROM "+ tableName +" WHERE " + idColumn +" = ?");
            ps.setLong(1,idEntity);
            rs = ps.executeQuery();
            entity = (Entity) mapper.toEntity(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return entity;
    }

    static List<?> findAll(String tableName, Mapper<?> mapper) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<?> entityList = null;
        try {
            ps = startConection("SELECT * FROM "+ tableName);
            rs = ps.executeQuery();
            entityList = mapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return entityList;
    }

    static Boolean delete(Long idEntity, String tableName, String idColumn) {
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = startConection("DELETE FROM " + tableName+ " WHERE "+idColumn+" = ?");
            ps.setLong(1,idEntity);
            int i = ps.executeUpdate();
            System.out.println("Registros eliminados: " + i);
            if(i > 0) {
                result = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return result;
    }

    static Boolean exists(Long idEntity, String tableName, String idColumn) {
        PreparedStatement ps = null;
        boolean result = false;
        try{
            ps = DaoBase.startConection("SELECT * FROM "+tableName+" WHERE "+idColumn+" = ?");
            ps.setLong(1, idEntity);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DaoBase.endConnection(ps);
        }
        return result;
    }

    static Long getLastId(String tableName, String idColumn){
        PreparedStatement ps = null;
        Long lastId = null;
        try{
            ps = startConection("SELECT MAX("+idColumn+") FROM "+tableName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                lastId = rs.getLong(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            endConnection(ps);
        }
        return lastId;
    }

    static List<?> findBy(String table, String column, String fieldValue, Mapper mapper) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<?> entityList = null;
        try {
            ps = startConection("SELECT * FROM "+ table +" WHERE " + column +" = ?");
            ps.setString(1,fieldValue);
            rs = ps.executeQuery();
            entityList = mapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return entityList;
    }

    static List<?> findBy(String table, String column, Long fieldValue, Mapper mapper) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<?> entityList = null;
        try {
            ps = startConection("SELECT * FROM "+ table +" WHERE " + column +" = ?");
            ps.setLong(1,fieldValue);
            rs = ps.executeQuery();
            entityList = mapper.toEntityList(rs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            endConnection(ps);
        }
        return entityList;
    }


}
