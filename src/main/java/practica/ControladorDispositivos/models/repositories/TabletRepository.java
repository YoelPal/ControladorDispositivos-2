package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Tablet;
@Repository
public interface TabletRepository extends JpaRepository<Tablet,String> {
}
