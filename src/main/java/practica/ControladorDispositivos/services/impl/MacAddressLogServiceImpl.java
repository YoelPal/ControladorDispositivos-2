package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.DtoConverterService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("MacAddressLog")
public class MacAddressLogServiceImpl implements IGenericDispService<MacAddressLogDTO,MacAddressLog, String> {

    private final MacAddressLogRepository macAddressLogRepository;
    private final IDtoConverterService dtoConverterService;
    private final ModelMapper modelMapper;

    public MacAddressLogServiceImpl(MacAddressLogRepository macAddressLogRepository, DtoConverterService dtoConverterService, ModelMapper modelMapper) {
        this.macAddressLogRepository = macAddressLogRepository;
        this.dtoConverterService = dtoConverterService;
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
        return dtoConverterService.convertToMacAddressLogDTO(savedLog);
    }

    @Override
    public boolean deleteById(String mac) {
        return false;
    }

    @Override
    public Optional<MacAddressLogDTO> update(MacAddressLog macAddressLog) {
        return Optional.empty();
    }
}
