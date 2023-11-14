package org.universidad.palermo.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<Entity> {

    List<Entity> toEntityList(ResultSet rs) throws SQLException;

    Entity toEntity(ResultSet rs) throws SQLException;

}
