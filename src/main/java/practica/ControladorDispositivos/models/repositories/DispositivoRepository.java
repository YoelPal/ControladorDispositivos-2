package practica.ControladorDispositivos.models.repositories;

import jakarta.annotation.Nonnull;
import jakarta.persistence.NamedEntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Pc;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, String>, JpaSpecificationExecutor<Dispositivo> {

    List<Dispositivo> findBySede(String sede);

    @Override
    @EntityGraph(attributePaths = "ips")
    @Nonnull
    Page<Dispositivo> findAll(
            @Nonnull Specification<Dispositivo> specification,
            @Nonnull Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "ips")
    @NonNull
    Optional<Dispositivo> findById(@NonNull String macAddress);


}
