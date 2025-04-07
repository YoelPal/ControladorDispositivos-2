package practica.ControladorDispositivos.services.impl;

import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.repositories.ApRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

public class ApServiceImpl implements IGenericDispService<Ap,String> {
    private final ApRepository apRepository;

    public ApServiceImpl(ApRepository apRepository) {
        this.apRepository = apRepository;
    }

    @Override
    public List<Ap> findAll() {
        return apRepository.findAll();
    }

    @Override
    public Optional<Ap> findById(String mac) {
        return apRepository.findById(mac);
    }

    @Override
    public Ap save(Ap entity) {
        return apRepository.save(entity);
    }

    @Override
    public boolean deleteById(String mac) {
        Optional<Ap> apOptional = apRepository.findById(mac);
        if (apOptional.isPresent()){
            apRepository.deleteById(mac);
        }
        return false;
    }

    @Override
    public Optional<Ap> update(Ap entity) {
        Optional<Ap> apOptional = apRepository.findById(entity.getMacAddress());
        if (apOptional.isPresent()){
            return Optional.of(apRepository.save(entity)) ;
        }
        return Optional.empty();
    }
}
