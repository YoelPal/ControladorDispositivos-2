package practica.ControladorDispositivos.services.impl;

import practica.ControladorDispositivos.models.entities.Tablet;
import practica.ControladorDispositivos.models.repositories.TabletRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

public class TabletServiceImpl implements IGenericDispService<Tablet,String> {
    private final TabletRepository tabletRepository;

    public TabletServiceImpl(TabletRepository tabletRepository) {
        this.tabletRepository = tabletRepository;
    }

    @Override
    public List<Tablet> findAll() {
        return tabletRepository.findAll();
    }

    @Override
    public Optional<Tablet> findById(String mac) {
        return tabletRepository.findById(mac);
    }

    @Override
    public Tablet save(Tablet entity) {
        return tabletRepository.save(entity);
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
    public Optional<Tablet> update(Tablet entity) {
        Optional<Tablet> tabletOpt = tabletRepository.findById(entity.getMacAddress());
        if (tabletOpt.isPresent()){
            return Optional.of(tabletRepository.save(entity));
        }
        return Optional.empty();
    }
}
