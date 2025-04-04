package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.entities.Tablet;

import java.util.List;
import java.util.Optional;

public interface IMacAddressLogService {

    public List<MacAddressLog> findAll();
    public Optional<MacAddressLog> findByMac(String mac);
    public MacAddressLog save(MacAddressLog macAddressLog);
    public boolean deleteByMac(String mac);
    public Optional<MacAddressLog> updateMacAddressLog(String mac, MacAddressLog macAddressLog);
}
