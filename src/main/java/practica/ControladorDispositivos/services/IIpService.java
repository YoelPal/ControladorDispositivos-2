package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.dto.IpDTO;
import practica.ControladorDispositivos.models.entities.Ip;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface IIpService extends IGenericDispService<IpDTO, Ip,Long>{

    void saveNewIpsByMac(List<MacAddressLog> macsConocidas);
}
