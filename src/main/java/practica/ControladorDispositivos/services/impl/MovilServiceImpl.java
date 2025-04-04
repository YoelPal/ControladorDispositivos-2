package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.repositories.MovilRepository;
import practica.ControladorDispositivos.services.IMovilService;

import java.util.List;
import java.util.Optional;

@Service
public class MovilServiceImpl implements IMovilService {
    private final MovilRepository movilRepository;

    public MovilServiceImpl(MovilRepository movilRepository) {
        this.movilRepository = movilRepository;
    }

    @Override
    public List<Movil> findAll() {
        return (List<Movil>) movilRepository.findAll();
    }

    @Override
    public Optional<Movil> findByMac(String mac) {
        return  movilRepository.findById(mac);
    }

    @Override
    public Movil save(Movil movil) {
        return movilRepository.save(movil);
    }

    @Override
    public boolean deleteByMac(String mac) {
        if (movilRepository.existsById(mac)) {
            movilRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Movil> updateMovil(String mac, Movil movil){
        Optional<Movil> movilOpt = movilRepository.findById(mac);
        if (movilOpt.isPresent()){
            movil.setMacAddress(mac);
            return Optional.of(movilRepository.save(movil));
        }
        return Optional.empty();
    }
}
