package practica.ControladorDispositivos.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGenericDispService<T,D, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(D entity);
    boolean deleteById(ID id);
    Optional<T> update(D entity);
    Optional<List<T>> findBySede(ID sede);
    Page<T> findAllPaginated(Pageable pageable, String macAddress,String sede, Boolean noCoincidentes);
}
