package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.repositories.MovilRepository;
import practica.ControladorDispositivos.services.IGenericDispService;


import java.util.List;
import java.util.Optional;

@Service("Movil")
public class MovilServiceImpl implements IGenericDispService<Movil,String> {
    private final MovilRepository movilRepository;

    public MovilServiceImpl(MovilRepository movilRepository) {
        this.movilRepository = movilRepository;
    }

    @Override
    public List<Movil> findAll() {
        return  movilRepository.findAll();
    }

    @Override
    public Optional<Movil> findById(String mac) {
        return  movilRepository.findById(mac);
    }

    @Override
    public Movil save(Movil movil) {
        return movilRepository.save(movil);
    }

    @Override
    public boolean deleteById(String mac) {
        if (movilRepository.existsById(mac)) {
            movilRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Movil> update( Movil movil){
        Optional<Movil> movilOpt = movilRepository.findById(movil.getMacAddress());
        if (movilOpt.isPresent()){
            return Optional.of(movilRepository.save(movil));
        }
        return Optional.empty();
    }
}
