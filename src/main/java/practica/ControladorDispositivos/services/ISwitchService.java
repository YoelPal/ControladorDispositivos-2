package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Router;
import practica.ControladorDispositivos.models.entities.Switch;

import java.util.List;
import java.util.Optional;

public interface ISwitchService {
    public List<Switch> findAll();
    public Optional<Switch> findByMac(String mac);
    public Switch save(Switch switchSave);
    public boolean deleteByMac(String mac);
    public Optional<Switch> updateSwitch(String mac, Switch switchUpdate);
}
