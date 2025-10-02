package practica.controladordispositivos.services.MacsManager;

import lombok.extern.slf4j.Slf4j;
import practica.controladordispositivos.models.entities.MacAddressLog;
import practica.controladordispositivos.models.repositories.MacAddressLogRepository;
import practica.controladordispositivos.services.MacsManager.Parsers.TxtParserService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@Service("txt")
public class TxtMacAddresProviderService implements IMacAddressProviderService{

    private final TxtParserService txtParserService;
    private final MacAddressLogRepository macAddressLogRepository;

    public TxtMacAddresProviderService(TxtParserService txtParserService, MacAddressLogRepository macAddressLogRepository) {
        this.txtParserService = txtParserService;
        this.macAddressLogRepository = macAddressLogRepository;
    }

    @Override
    public List<MacAddressLog> obtenerLogs(MultipartFile file) {
        return txtParserService.txtParser(file);
    }

    @Override
    public void guardarLogs(List<MacAddressLog> lista) {
        long t0 = System.currentTimeMillis();
        macAddressLogRepository.saveAll(lista);
        log.info("TXT: parse {} ms, insert {} ms", System.currentTimeMillis() - t0, 0);

    }
}
