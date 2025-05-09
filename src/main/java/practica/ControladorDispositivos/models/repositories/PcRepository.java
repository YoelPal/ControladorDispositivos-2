package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Pc;


import java.util.List;

@Repository
public interface PcRepository extends JpaRepository<Pc,String> {
    List<Pc> findBySede(String sede);
}
