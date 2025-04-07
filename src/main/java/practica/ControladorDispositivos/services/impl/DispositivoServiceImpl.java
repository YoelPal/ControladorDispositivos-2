package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.repositories.DispositivoRepository;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@Service("Dispositivo")
public class DispositivoServiceImpl implements IGenericDispService<Dispositivo,String> {
    private final DispositivoRepository dispositivoRepository;

    public DispositivoServiceImpl(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    @Override
    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    @Override
    public Optional<Dispositivo> findById(String mac) {
        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.findById(mac);
        if (dispositivoOpt.isPresent()){
            return Optional.of(dispositivoOpt.get());
        }
        return Optional.empty();
    }

    @Override
    public Dispositivo save(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    @Override
    public boolean deleteById(String mac) {
        if (dispositivoRepository.existsById(mac)){
            dispositivoRepository.deleteById(mac);
            return true;
        }
        return false;
    }
    @Override
    public Optional<Dispositivo> update(Dispositivo dispositivo) {
        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.findById(dispositivo.getMacAddress());
        if (dispositivoOpt.isPresent()){
           return Optional.of(dispositivoRepository.save(dispositivo)) ;
        }
        return Optional.empty();
    }
}
