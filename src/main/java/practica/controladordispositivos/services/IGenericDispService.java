package practica.controladordispositivos.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IGenericDispService<T,D, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(D entity);
    boolean deleteById(ID id);
    Optional<T> update(D entity);
    Optional<List<T>> findBySede(String sede);

    Page<T> findAllPaginated(Pageable pageable, Specification<D> spec);
}
