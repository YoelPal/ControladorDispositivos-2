package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Movil;
@Repository
public interface MovilRepository extends JpaRepository<Movil, String> {
}
