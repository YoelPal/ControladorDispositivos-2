package practica.ControladorDispositivos.services;

import java.util.List;
import java.util.Optional;

public interface IGenericDispService<T,D, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(D entity);
    boolean deleteById(ID id);
    Optional<T> update(D entity);
    Optional<List<T>> findBySede(ID sede);
}
