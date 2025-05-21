package practica.ControladorDispositivos.services.MacsManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.MacsManager.Parsers.CsvParserService;

import java.util.List;
@Service("csvDataSource")
public class CsvMacAddressProviderService implements IMacAddressProviderService {
    private final CsvParserService csvParserService;
    private final MacAddressLogRepository macAddressLogRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvMacAddressProviderService.class);
    long t0;

    public CsvMacAddressProviderService(CsvParserService csvParserService, MacAddressLogRepository macAddressLogRepository) {
        this.csvParserService = csvParserService;
        this.macAddressLogRepository = macAddressLogRepository;
    }



    @Override
    public List<MacAddressLog> obtenerLogs(MultipartFile file) {
        return csvParserService.CsvValoresPorCabeceras(file);

    }

    @Override
    public void guardarLogs(List<MacAddressLog> lista) {
        long t1 = System.currentTimeMillis();
        macAddressLogRepository.saveAll(lista);
        long t2 = System.currentTimeMillis();
        LOGGER.info("CSV parse: {} ms, DB insert: {} ms", t1-t0, t2-t1);


    }
}
