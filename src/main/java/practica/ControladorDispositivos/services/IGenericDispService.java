package practica.ControladorDispositivos.services;

import java.util.List;
import java.util.Optional;

public interface IGenericDispService<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    boolean deleteById(ID id);
    Optional<T> update(T entity);
}
