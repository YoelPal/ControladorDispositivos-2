package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import javax.crypto.Mac;
import java.util.List;
import java.util.Optional;

@Repository
public interface MacAddressLogRepository extends JpaRepository<MacAddressLog, Long>, JpaSpecificationExecutor<MacAddressLog> {

    @Query("select l from MacAddressLog l left join Dispositivo d on l.macAddress = d.macAddress where d.macAddress is null")
    List<MacAddressLog> listaLogsNoRegistrados();
    @Query("select l from MacAddressLog l inner join Dispositivo d on l.macAddress = d.macAddress")
    List<MacAddressLog> listaLogsRegistradas();

    @Query("select l.macAddress from MacAddressLog l left join Dispositivo d on l.macAddress = d.macAddress where l.macAddress is null")
    List<String> listaMAcsNoRegistrados();

    List<MacAddressLog> findBySede(String sede);

    Page<MacAddressLog> findAll(Specification<MacAddressLog> spec, Pageable pageable);




    Optional<MacAddressLog> findByMacAddress(String macAddress);
}
