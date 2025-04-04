package practica.ControladorDispositivos.services.MacsProvider;

import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface MacAddressProviderService {
    List<MacAddressLog> obtenerLogs();
    void guardarLogs(List<MacAddressLog> lista);

}
