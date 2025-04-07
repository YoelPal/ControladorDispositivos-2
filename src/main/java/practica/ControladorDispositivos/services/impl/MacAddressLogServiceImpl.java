package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@Service("MacAddressLog")
public class MacAddressLogServiceImpl implements IGenericDispService<MacAddressLog,String> {

    private final MacAddressLogRepository macAddressLogRepository;

    public MacAddressLogServiceImpl(MacAddressLogRepository macAddressLogRepository) {
        this.macAddressLogRepository = macAddressLogRepository;
    }


    @Override
    public List<MacAddressLog> findAll() {
        return List.of();
    }

    @Override
    public Optional<MacAddressLog> findById(String mac) {
        return Optional.empty();
    }

    @Override
    public MacAddressLog save(MacAddressLog macAddressLog) {
        return macAddressLogRepository.save(macAddressLog);
    }

    @Override
    public boolean deleteById(String mac) {
        return false;
    }

    @Override
    public Optional<MacAddressLog> update(MacAddressLog macAddressLog) {
        return Optional.empty();
    }
}
