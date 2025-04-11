package practica.ControladorDispositivos.services.MacsManager;

import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface IMacAddressProviderService {
    List<MacAddressLog> obtenerLogs();
    void guardarLogs(List<MacAddressLog> lista);

}
