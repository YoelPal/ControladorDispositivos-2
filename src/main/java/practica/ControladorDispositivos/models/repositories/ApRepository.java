package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Ap;

import java.util.List;

@Repository
public interface ApRepository extends JpaRepository<Ap,String> {

    List<Ap> findBySede(String sede);
}
