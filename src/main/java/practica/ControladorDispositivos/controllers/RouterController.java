package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.RouterDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Router;
import practica.ControladorDispositivos.services.IGenericDispService;

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
