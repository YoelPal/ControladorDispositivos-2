package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.Dispositivo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, String> {


}
