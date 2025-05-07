package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.SwitchDTO;
import practica.ControladorDispositivos.models.entities.Switch;
import practica.ControladorDispositivos.models.repositories.SwitchRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("switch")
public class SwitchServiceImpl implements IGenericDispService<SwitchDTO,Switch,String> {
    private final SwitchRepository switchRepository;
    private final IDtoConverterService dtoConverterService;
    private final ModelMapper modelMapper;

    public SwitchServiceImpl(SwitchRepository switchRepository, IDtoConverterService dtoConverterService, ModelMapper modelMapper) {
        this.switchRepository = switchRepository;
        this.dtoConverterService = dtoConverterService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SwitchDTO> findAll() {
        return switchRepository.findAll()
                .stream()
                .map(entity->modelMapper.map(entity, SwitchDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SwitchDTO> findById(String macAddress) {
        return switchRepository.findById(macAddress).map(entity->modelMapper.map(entity, SwitchDTO.class));
    }

    @Override
    public SwitchDTO save(Switch entity) {
        Switch savedSwitch = switchRepository.save(entity);
        return modelMapper.map(savedSwitch, SwitchDTO.class);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Switch> switchOptional = switchRepository.findById(mac);
        if (switchOptional.isPresent()){
            switchRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<SwitchDTO> update(Switch entity) {
        Optional<Switch> switchOptional = switchRepository.findById(entity.getMacAddress());
        if (switchOptional.isPresent()){
            Switch updatedSwitch = switchRepository.save(entity);
            return Optional.of(modelMapper.map(updatedSwitch, SwitchDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<SwitchDTO>> findBySede(String sede) {
        List<SwitchDTO> switchDTOList = switchRepository.findBySede(sede).stream()
                .map(entity->(modelMapper.map(entity, SwitchDTO.class)))
                .toList();
        if (switchDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(switchDTOList);
    }
}
