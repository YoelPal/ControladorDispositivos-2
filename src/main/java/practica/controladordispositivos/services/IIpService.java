package practica.controladordispositivos.services;

import java.util.List;
import java.util.Optional;

import practica.controladordispositivos.models.dto.IpDTO;
import practica.controladordispositivos.models.entities.Ip;
import practica.controladordispositivos.models.entities.MacAddressLog;

public interface IIpService extends IGenericDispService<IpDTO, Ip,Long>{

    void saveNewIpsByMac(List<MacAddressLog> macsConocidas);



    Optional<Ip> findByIpAddress(String ipAddress);
}
