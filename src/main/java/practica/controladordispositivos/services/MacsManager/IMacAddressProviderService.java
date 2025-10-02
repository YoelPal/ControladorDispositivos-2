package practica.ControladorDispositivos.services.MacsManager;

import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface IMacAddressProviderService {
    List<MacAddressLog> obtenerLogs(MultipartFile file);
    void guardarLogs(List<MacAddressLog> lista);

}
