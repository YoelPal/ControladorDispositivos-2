package practica.ControladorDispositivos.services.MacsManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.MacsManager.Parsers.TxtParserService;

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
