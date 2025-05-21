package practica.ControladorDispositivos.models.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.entities.Router;


import java.util.List;
import java.util.Optional;

@Repository
public interface RouterRepository extends JpaRepository<Router,String> , JpaSpecificationExecutor<Router> {
    List<Router> findBySede(String sede);

    @Override
    @EntityGraph(attributePaths = "ips")
    @Nonnull
    Page<Router> findAll(
            @Nonnull Specification<Router> specification,
            @Nonnull Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Optional<Router> findById(@NonNull String macAddress);

}
