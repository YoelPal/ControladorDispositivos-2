package practica.controladordispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import practica.controladordispositivos.models.dto.SwitchDTO;
import practica.controladordispositivos.models.entities.Ip;
import practica.controladordispositivos.models.entities.Switch;
import practica.controladordispositivos.models.repositories.SwitchRepository;
import practica.controladordispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("switch")
public class SwitchServiceImpl implements IGenericDispService<SwitchDTO,Switch,String> {
    private final SwitchRepository switchRepository;
    private final ModelMapper modelMapper;

    public SwitchServiceImpl(SwitchRepository switchRepository, ModelMapper modelMapper) {
        this.switchRepository = switchRepository;
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
        for(Ip ip : entity.getIps()){
            ip.setDispositivo(entity);
        }
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
            switchOptional.get().getIps().clear();
            for (Ip ip : entity.getIps()){
                ip.setDispositivo(switchOptional.get());
                switchOptional.get().addIp(ip);

            }
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

    @Override
    public Page<SwitchDTO> findAllPaginated(Pageable pageable, Specification<Switch> spec) {
        Page<Switch> switchPage = switchRepository.findAll(spec,pageable);

        return switchPage.map(entity -> modelMapper.map(entity, SwitchDTO.class));
    }


}
