package practica.ControladorDispositivos.services.impl;

import practica.ControladorDispositivos.models.entities.Switch;
import practica.ControladorDispositivos.models.repositories.SwitchRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

public class SwitchServiceImpl implements IGenericDispService<Switch,String> {
    private final SwitchRepository switchRepository;

    public SwitchServiceImpl(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    @Override
    public List<Switch> findAll() {
        return switchRepository.findAll();
    }

    @Override
    public Optional<Switch> findById(String mac) {
        return switchRepository.findById(mac);
    }

    @Override
    public Switch save(Switch entity) {
        return switchRepository.save(entity);
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
    public Optional<Switch> update(Switch entity) {
        Optional<Switch> switchOptional = switchRepository.findById(entity.getMacAddress());
        if (switchOptional.isPresent()){
            return Optional.of(switchRepository.save(entity));
        }
        return Optional.empty();
    }
}
