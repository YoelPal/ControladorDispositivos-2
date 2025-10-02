package practica.controladordispositivos.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import practica.controladordispositivos.models.dto.MacAddressLogDTO;
import practica.controladordispositivos.models.entities.MacAddressLog;

import java.util.Optional;


public interface IMacAddressLogService extends IGenericDispService<MacAddressLogDTO, MacAddressLog,Long> {

        Page<MacAddressLogDTO> findAllPaginated(Pageable pageable, String macAddress, String sede, Boolean noCoincidentes);

        boolean deleteLogsUpdated();

        Optional<MacAddressLog> findByMacAddress(String macAddress);
        }