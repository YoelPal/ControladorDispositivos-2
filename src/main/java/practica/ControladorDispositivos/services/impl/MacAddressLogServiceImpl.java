package practica.ControladorDispositivos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.IMacAddressLogService;

import java.util.List;
import java.util.Optional;

@Service
public class MacAddressLogServiceImpl implements IMacAddressLogService {

    private final MacAddressLogRepository macAddressLogRepository;

    public MacAddressLogServiceImpl(MacAddressLogRepository macAddressLogRepository) {
        this.macAddressLogRepository = macAddressLogRepository;
    }


    @Override
    public List<MacAddressLog> findAll() {
        return List.of();
    }

    @Override
    public Optional<MacAddressLog> findByMac(String mac) {
        return Optional.empty();
    }

    @Override
    public MacAddressLog save(MacAddressLog macAddressLog) {
        return macAddressLogRepository.save(macAddressLog);
    }

    @Override
    public boolean deleteByMac(String mac) {
        return false;
    }

    @Override
    public Optional<MacAddressLog> updateMacAddressLog(String mac, MacAddressLog macAddressLog) {
        return Optional.empty();
    }
}
