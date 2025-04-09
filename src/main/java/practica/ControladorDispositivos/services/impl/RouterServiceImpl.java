package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.dto.RouterDTO;
import practica.ControladorDispositivos.models.entities.Router;
import practica.ControladorDispositivos.models.repositories.RouterRepository;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.dtoConverter.DtoConverterService;
import practica.ControladorDispositivos.services.dtoConverter.IDtoConverterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Router")
public class RouterServiceImpl implements IGenericDispService<RouterDTO,Router,String> {
    private final RouterRepository routerRepository;
    private final IDtoConverterService dtoConverterService;

    public RouterServiceImpl(RouterRepository routerRepository, IDtoConverterService dtoConverterService) {
        this.routerRepository = routerRepository;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public List<RouterDTO> findAll() {
        return routerRepository.findAll()
                .stream()
                .map(dtoConverterService::convertToRouterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RouterDTO> findById(String mac) {
        return routerRepository.findById(mac).map(dtoConverterService::convertToRouterDTO);
    }

    @Override
    public RouterDTO save(Router entity) {
        Router savedRouter = routerRepository.save(entity);
        return dtoConverterService.convertToRouterDTO(savedRouter);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Router> routerOpt = routerRepository.findById(mac);
        if (routerOpt.isPresent()){
            routerRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<RouterDTO> update(Router entity) {
        Optional<Router> routerOpt = routerRepository.findById(entity.getMacAddress());
        if (routerOpt.isPresent()) {
            Router updatedRouter = routerRepository.save(entity);
            return Optional.of(dtoConverterService.convertToRouterDTO(updatedRouter)) ;
        }
        return Optional.empty();
    }
}
