package practica.ControladorDispositivos.services.impl;

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

    public SwitchServiceImpl(SwitchRepository switchRepository, IDtoConverterService dtoConverterService) {
        this.switchRepository = switchRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<SwitchDTO> findAll() {
        return switchRepository.findAll()
                .stream()
                .map(dtoConverterService::convertToSwitchDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SwitchDTO> findById(String mac) {
        return switchRepository.findById(mac).map(dtoConverterService::convertToSwitchDTO);
    }

    @Override
    public SwitchDTO save(Switch entity) {
        Switch savedSwitch = switchRepository.save(entity);
        return dtoConverterService.convertToSwitchDTO(savedSwitch);
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
            return Optional.of(dtoConverterService.convertToSwitchDTO(updatedSwitch));
        }
        return Optional.empty();
    }
}
