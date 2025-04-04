package practica.ControladorDispositivos.services.MacsProvider;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;

import java.util.List;
@Service("csvDataSource")
public class CsvMacAddressProviderService implements MacAddressProviderService{
    private final CsvParserService csvParserService;
    private final MacAddressLogRepository macAddressLogRepository;

    public CsvMacAddressProviderService(CsvParserService csvParserService, MacAddressLogRepository macAddressLogRepository) {
        this.csvParserService = csvParserService;
        this.macAddressLogRepository = macAddressLogRepository;
    }
    @Setter
    private MultipartFile file;

    @Override
    public List<MacAddressLog> obtenerLogs() {
        return csvParserService.obtenerMacsOpenCSV(file);
    }

    @Override
    public void guardarLogs(List<MacAddressLog> lista) {
        macAddressLogRepository.saveAll(lista);


    }
}
