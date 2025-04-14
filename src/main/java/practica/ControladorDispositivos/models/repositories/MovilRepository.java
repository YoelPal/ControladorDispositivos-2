package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Tablet;

import java.util.List;

@Repository
public interface MovilRepository extends JpaRepository<Movil, String> {
    List<Movil> findBySede(String sede);
}
