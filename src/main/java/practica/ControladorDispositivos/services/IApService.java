package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Ap;

import java.util.List;
import java.util.Optional;

public interface IApService {
    public List<Ap> findAll();
    public Optional<Ap> findByMac(String mac);
    public Ap save(Ap ap);
    public boolean deleteByMac(String mac);
    public Optional<Ap> updateAp(String mac, Ap ac);
}
