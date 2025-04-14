package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Tablet;

import java.util.List;

@Repository
public interface TabletRepository extends JpaRepository<Tablet,String> {
    List<Tablet> findBySede(String sede);
}
