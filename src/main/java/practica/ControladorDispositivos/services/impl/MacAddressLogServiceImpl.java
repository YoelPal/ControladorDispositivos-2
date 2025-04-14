package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("MacAddressLog")
public class MacAddressLogServiceImpl implements IGenericDispService<MacAddressLogDTO,MacAddressLog, String> {

    private final MacAddressLogRepository macAddressLogRepository;
    private final ModelMapper modelMapper;

    public MacAddressLogServiceImpl(MacAddressLogRepository macAddressLogRepository,ModelMapper modelMapper) {
        this.macAddressLogRepository = macAddressLogRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MacAddressLogDTO> findAll() {
        return macAddressLogRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity,MacAddressLogDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MacAddressLogDTO> findById(String mac) {
        return Optional.empty();
    }

    @Override
    public MacAddressLogDTO save(MacAddressLog macAddressLog) {
        MacAddressLog savedLog = macAddressLogRepository.save(macAddressLog);
        return modelMapper.map(savedLog, MacAddressLogDTO.class);
    }

    @Override
    public boolean deleteById(String mac) {
        return false;
    }

    @Override
    public Optional<MacAddressLogDTO> update(MacAddressLog macAddressLog) {
        return Optional.empty();
    }

    @Override
    public Optional<List<MacAddressLogDTO>> findBySede(String sede){
        List<MacAddressLogDTO> listaLogs = macAddressLogRepository.findBySede(sede)
                .stream()
                .map(entity->modelMapper.map(entity,MacAddressLogDTO.class))
                .toList();
        if (!listaLogs.isEmpty()){
            return Optional.of(listaLogs);
        }
        return Optional.empty();
    }
}
