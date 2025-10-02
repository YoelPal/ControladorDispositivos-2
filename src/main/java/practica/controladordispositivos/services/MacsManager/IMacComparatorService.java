package practica.ControladorDispositivos.services.MacsManager;

import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface IMacComparatorService {
    List<MacAddressLog> listaLogsNoCoincidentes();


}
