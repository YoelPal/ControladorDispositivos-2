package practica.ControladorDispositivos.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

@Repository
public interface MacAddressLogRepository extends JpaRepository<MacAddressLog, String> {

    @Query("select l from MacAddressLog l left join Dispositivo d on l.macAddress = d.macAddress where d.macAddress is null")
    List<MacAddressLog> listaLogsNoRegistrados();

    @Query("select l.macAddress from MacAddressLog l left join Dispositivo d on l.macAddress = d.macAddress where l.macAddress is null")
    List<String> listaMAcsNoRegistrados();

    List<MacAddressLog> findBySede(String sede);



}
