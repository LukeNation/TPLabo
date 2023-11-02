package org.universidad.palermo.dao.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public interface DaoBase<Type, ID>{

    String DB_URL = "jdbc:postgresql://localhost:2300/postgres";
    String DB_USER ="postgres";
    String DB_PASSWORD = "password";

    Type findById(ID idEntity);

    List<Type> findAll();

    Type save (Type entity);

    Boolean delete (ID idEntity);

    Long count();

    Boolean exists(ID idEntity);

    static Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
