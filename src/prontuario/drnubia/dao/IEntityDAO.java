package prontuario.drnubia.dao;

import java.util.List;

public interface IEntityDAO<T> {

    void create(T t);

    T findById(Long id);

    void update(T t);

    void delete(T t);

    List<T> findAll();
}
