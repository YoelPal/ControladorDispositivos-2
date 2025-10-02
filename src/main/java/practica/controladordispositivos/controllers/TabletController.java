package practica.controladordispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import practica.controladordispositivos.models.dto.DispositivoDTO;
import practica.controladordispositivos.models.dto.TabletDTO;
import practica.controladordispositivos.models.entities.Dispositivo;
import practica.controladordispositivos.models.entities.Tablet;
import practica.controladordispositivos.services.IGenericDispService;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


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
