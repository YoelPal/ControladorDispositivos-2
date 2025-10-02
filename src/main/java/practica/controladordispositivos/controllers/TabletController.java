package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.TabletDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Tablet;
import practica.ControladorDispositivos.services.IGenericDispService;


@RestController
@RequestMapping("/tablet")
@Tag(name = "Tablets", description = "Dispositivos Tablet almacenados con MAC conocida.")
public class TabletController extends GenericDeviceController<TabletDTO, Tablet, String> {

    public TabletController(IGenericDispService<TabletDTO, Tablet, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);
    }

    @Override
    protected String extractId(TabletDTO tabletDTO) {
        return tabletDTO.getMacAddress();
    }

    @Override
    protected Class<Tablet> getEntityClass() {
        return Tablet.class;
    }
}
