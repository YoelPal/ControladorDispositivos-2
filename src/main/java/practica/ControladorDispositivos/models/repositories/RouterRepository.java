package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Router;
import practica.ControladorDispositivos.models.entities.Tablet;

import java.util.List;

@Repository
public interface RouterRepository extends JpaRepository<Router,String> {
    List<Router> findBySede(String sede);
}
