package practica.ControladorDispositivos.services.impl;

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

    public TabletServiceImpl(TabletRepository tabletRepository, IDtoConverterService dtoConverterService) {
        this.tabletRepository = tabletRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<TabletDTO> findAll() {
        return tabletRepository.findAll()
                .stream()
                .map(dtoConverterService::convertToTabletDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TabletDTO> findById(String mac) {
        return tabletRepository.findById(mac).map(dtoConverterService::convertToTabletDTO);
    }

    @Override
    public TabletDTO save(Tablet entity) {
        Tablet savedTablet = tabletRepository.save(entity);
        return dtoConverterService.convertToTabletDTO(savedTablet);
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
            return Optional.of(dtoConverterService.convertToTabletDTO(updatedTablet));
        }
        return Optional.empty();
    }
}
