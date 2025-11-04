package dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    void add(T item);
    Optional<T> getById(int id);
    List<T> getAll();
    void update(T item);
    void delete(int id);
}
