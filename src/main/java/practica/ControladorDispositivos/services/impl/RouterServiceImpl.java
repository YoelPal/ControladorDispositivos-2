package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Router;
import practica.ControladorDispositivos.models.repositories.RouterRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@Service("Router")
public class RouterServiceImpl implements IGenericDispService<Router,String> {
    private final RouterRepository routerRepository;

    public RouterServiceImpl(RouterRepository routerRepository) {
        this.routerRepository = routerRepository;
    }

    @Override
    public List<Router> findAll() {
        return routerRepository.findAll();
    }

    @Override
    public Optional<Router> findById(String mac) {
        return routerRepository.findById(mac);
    }

    @Override
    public Router save(Router entity) {
        return routerRepository.save(entity);
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
    public Optional<Router> update(Router entity) {
        Optional<Router> routerOpt = routerRepository.findById(entity.getMacAddress());
        if (routerOpt.isPresent()) {
            return Optional.of(routerRepository.save(entity)) ;
        }
        return Optional.empty();
    }
}
