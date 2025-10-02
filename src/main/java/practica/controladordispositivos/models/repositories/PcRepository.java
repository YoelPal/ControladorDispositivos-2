package practica.controladordispositivos.models.repositories;

import jakarta.annotation.Nonnull;
import practica.controladordispositivos.models.entities.Pc;

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
public interface PcRepository extends JpaRepository<Pc,String>, JpaSpecificationExecutor<Pc> {
    List<Pc> findBySede(String sede);


    @Override
    @EntityGraph(attributePaths = "ips")
    @Nonnull
    Page<Pc> findAll(
            @Nonnull Specification<Pc> specification,
            @Nonnull Pageable pageable);


    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Optional<Pc> findById(@NonNull String macAddress);
}
