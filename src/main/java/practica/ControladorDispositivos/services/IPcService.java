package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.models.repositories.PcRepository;

import java.util.List;
import java.util.Optional;

public interface IPcService {
    public List<Pc> findAll();
    public Optional<Pc> findByMac(String mac);
    public Pc save(Pc pc);
    public boolean deleteByMac(String mac);
    public Optional<Pc> updatePc(Pc pc);
}
