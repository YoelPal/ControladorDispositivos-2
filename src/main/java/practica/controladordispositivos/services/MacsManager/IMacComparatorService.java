package practica.controladordispositivos.services.MacsManager;

import java.util.List;

import practica.controladordispositivos.models.entities.MacAddressLog;

public interface IMacComparatorService {
    List<MacAddressLog> listaLogsNoCoincidentes();


}
