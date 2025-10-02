package practica.controladordispositivos.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import practica.controladordispositivos.models.dto.RouterDTO;
import practica.controladordispositivos.models.entities.Ip;
import practica.controladordispositivos.models.entities.Router;
import practica.controladordispositivos.models.repositories.RouterRepository;
import practica.controladordispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Router")
public class RouterServiceImpl implements IGenericDispService<RouterDTO,Router,String> {
    private final RouterRepository routerRepository;
    private final ModelMapper modelMapper;

    public RouterServiceImpl(RouterRepository routerRepository, ModelMapper modelMapper) {
        this.routerRepository = routerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RouterDTO> findAll() {
        return routerRepository.findAll()
                .stream()
                .map(entity->modelMapper.map(entity, RouterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RouterDTO> findById(String macAddress) {
        return routerRepository.findById(macAddress).map(entity->modelMapper.map(entity, RouterDTO.class));
    }

    @Override
    public RouterDTO save(Router entity) {
        for(Ip ip : entity.getIps()){
            ip.setDispositivo(entity);
        }
        Router savedRouter = routerRepository.save(entity);
        return modelMapper.map(savedRouter,RouterDTO.class);
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
            routerOpt.get().getIps().clear();
            for (Ip ip : entity.getIps()){
                ip.setDispositivo(routerOpt.get());
                routerOpt.get().addIp(ip);
            }
            Router updatedRouter = routerRepository.save(entity);
            return Optional.of(modelMapper.map(updatedRouter, RouterDTO.class)) ;
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<RouterDTO>> findBySede(String sede) {
        List<RouterDTO> routerDTOList = routerRepository.findBySede(sede).stream()
                .map(entity->modelMapper.map(entity, RouterDTO.class))
                .toList();
        if (routerDTOList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(routerDTOList);
    }

    @Override
    public Page<RouterDTO> findAllPaginated(Pageable pageable, Specification<Router> spec) {
        Page<Router> routerPage = routerRepository.findAll(spec,pageable);

        return routerPage.map(entity -> modelMapper.map(entity, RouterDTO.class));
    }


}
