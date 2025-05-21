package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Ap;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApRepository extends JpaRepository<Ap,String>, JpaSpecificationExecutor<Ap> {

    List<Ap> findBySede(String sede);

    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Page<Ap> findAll(
            @NonNull Specification<Ap> specification,
            @NonNull Pageable pageable);


    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Optional<Ap> findById(@NonNull String macAddress);


}
