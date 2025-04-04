package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface IDispositivoService {
    public List<Dispositivo> findAll();
    public Optional<Dispositivo> findByMac(String mac);
    public Dispositivo save(Dispositivo dispositivo);
    public boolean deleteByMac(String mac);
    public Optional<Dispositivo> updateDispositivo(String mac, Dispositivo dispositivo);
}
