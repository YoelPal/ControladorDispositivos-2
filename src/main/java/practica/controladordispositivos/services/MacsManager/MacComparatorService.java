package practica.controladordispositivos.services.MacsManager;

import org.springframework.stereotype.Service;

import practica.controladordispositivos.models.entities.MacAddressLog;
import practica.controladordispositivos.models.repositories.MacAddressLogRepository;

import java.util.List;

@Service
public class MacComparatorService implements IMacComparatorService{
    private final MacAddressLogRepository macAddressLogRepository;

    public MacComparatorService(MacAddressLogRepository macAddressLogRepository) {
        this.macAddressLogRepository = macAddressLogRepository;
    }

    @Override
    public List<MacAddressLog> listaLogsNoCoincidentes() {
        return macAddressLogRepository.listaLogsNoRegistrados();
    }
}
