package practica.ControladorDispositivos.services.MacsProvider;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;

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
