package practica.ControladorDispositivos.services;

import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.entities.Router;

import java.util.List;
import java.util.Optional;

public interface IRouterService {
    public List<Router> findAll();
    public Optional<Router> findByMac(String mac);
    public Router save(Router router);
    public boolean deleteByMac(String mac);
    public Optional<Router> updateRouter(String mac, Router router);
}
