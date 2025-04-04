package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.repositories.DispositivoRepository;
import practica.ControladorDispositivos.services.IDispositivoService;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoServiceImpl implements IDispositivoService {
    private final DispositivoRepository dispositivoRepository;

    public DispositivoServiceImpl(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    @Override
    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    @Override
    public Optional<Dispositivo> findByMac(String mac) {
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
    public boolean deleteByMac(String mac) {
        if (dispositivoRepository.existsById(mac)){
            dispositivoRepository.deleteById(mac);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Dispositivo> updateDispositivo(String mac, Dispositivo dispositivo) {
        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.findById(mac);
        if (dispositivoOpt.isPresent()){
           return Optional.of(dispositivoRepository.save(dispositivo)) ;
        }
        return Optional.empty();
    }
}
