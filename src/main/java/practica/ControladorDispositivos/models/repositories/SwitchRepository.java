package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Switch;


import java.util.List;

@Repository
public interface SwitchRepository extends JpaRepository<Switch,String> {
    List<Switch> findBySede(String sede);
}
