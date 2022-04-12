package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDAO <T> {
    List<T> selectAll();

    void insert(T t);

    T getById(int id);

    boolean delete(int id) throws SQLException;

    boolean update(T t) throws SQLException;
}
