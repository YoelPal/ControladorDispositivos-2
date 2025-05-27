package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.dto.IpDTO;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;
import java.util.Optional;

public interface IIpService extends IGenericDispService<IpDTO, Ip,Long>{

    void saveNewIpsByMac(List<MacAddressLog> macsConocidas);



    Optional<Ip> findByIpAddress(String ipAddress);
}
