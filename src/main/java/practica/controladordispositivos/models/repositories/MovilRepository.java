package practica.controladordispositivos.models.repositories;

import jakarta.annotation.Nonnull;
import practica.controladordispositivos.models.entities.Movil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovilRepository extends JpaRepository<Movil, String>, JpaSpecificationExecutor<Movil> {
    List<Movil> findBySede(String sede);

    @Override
    @EntityGraph(attributePaths = "ips")
    @Nonnull
    Page<Movil> findAll(
            @Nonnull Specification<Movil> specification,
            @Nonnull Pageable pageable);


    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Optional<Movil> findById(@NonNull String macAddress);
}
