package practica.ControladorDispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.TabletDTO;
import practica.ControladorDispositivos.models.entities.Tablet;
import practica.ControladorDispositivos.models.repositories.TabletRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("tablet")
public class TabletServiceImpl implements IGenericDispService<TabletDTO,Tablet,String> {
    private final TabletRepository tabletRepository;
    private final IDtoConverterService dtoConverterService;
    private final ModelMapper modelMapper;

    public TabletServiceImpl(TabletRepository tabletRepository, IDtoConverterService dtoConverterService, ModelMapper modelMapper) {
        this.tabletRepository = tabletRepository;
        this.dtoConverterService = dtoConverterService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TabletDTO> findAll() {
        return tabletRepository.findAll()
                .stream()
                .map(entity->(modelMapper.map(entity, TabletDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TabletDTO> findById(String macAddress) {
        return tabletRepository.findById(macAddress).map(entity->modelMapper.map(entity, TabletDTO.class));
    }

    @Override
    public TabletDTO save(Tablet entity) {
        Tablet savedTablet = tabletRepository.save(entity);
        return modelMapper.map(savedTablet, TabletDTO.class);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Tablet> tabletOpt = tabletRepository.findById(mac);
        if (tabletOpt.isPresent()){
            tabletRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<TabletDTO> update(Tablet entity) {
        Optional<Tablet> tabletOpt = tabletRepository.findById(entity.getMacAddress());
        if (tabletOpt.isPresent()){
            Tablet updatedTablet = tabletRepository.save(entity);
            return Optional.of(modelMapper.map(updatedTablet, TabletDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<TabletDTO>> findBySede(String sede) {
        List<TabletDTO> tabletDTOList = tabletRepository.findBySede(sede).stream()
                .map(entity->modelMapper.map(entity, TabletDTO.class))
                .toList();
        if (tabletDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(tabletDTOList);
    }
}
