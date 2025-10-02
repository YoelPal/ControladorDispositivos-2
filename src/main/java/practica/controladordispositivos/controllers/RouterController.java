package practica.controladordispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import practica.controladordispositivos.models.dto.DispositivoDTO;
import practica.controladordispositivos.models.dto.RouterDTO;
import practica.controladordispositivos.models.entities.Dispositivo;
import practica.controladordispositivos.models.entities.Router;
import practica.controladordispositivos.services.IGenericDispService;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/router")
@Tag(name = "Router", description = "Dispositivos Router almacenados con MAC conocida.")
public class RouterController extends GenericDeviceController<RouterDTO, Router,String> {
    public RouterController(IGenericDispService<RouterDTO, Router, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);
    }

    @Override
    protected String extractId(RouterDTO routerDTO) {
        return routerDTO.getMacAddress();
    }

    @Override
    protected Class<Router> getEntityClass() {
        return Router.class;
    }
}
