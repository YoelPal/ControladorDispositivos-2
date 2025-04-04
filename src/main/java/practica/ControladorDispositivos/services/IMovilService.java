package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Movil;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IMovilService {
    public List<Movil> findAll();
    public Optional<Movil> findByMac(String mac);
    public Movil save(Movil movil);
    public boolean deleteByMac(String mac);
    public Optional<Movil> updateMovil(String mac, Movil movil);

}
