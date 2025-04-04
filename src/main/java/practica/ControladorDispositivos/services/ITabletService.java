package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Tablet;

import java.util.List;
import java.util.Optional;

public interface ITabletService {
    public List<Tablet> findAll();
    public Optional<Tablet> findByMac(String mac);
    public Tablet save(Tablet tablet);
    public boolean deleteByMac(String mac);
    public Optional<Tablet> updateTablet(String mac, Tablet tablet);
}
