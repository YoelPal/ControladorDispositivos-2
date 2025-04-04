package practica.ControladorDispositivos.services.MacsProvider;

import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.util.List;

public interface MacAddressProviderService {
    List<DispositivoDTO> obtenerMacs(MultipartFile file);
    List<MacAddressLog> obtenerMacsOpenCSV(MultipartFile file);
    void guardarDispositivos(List<MacAddressLog> lista);

}
