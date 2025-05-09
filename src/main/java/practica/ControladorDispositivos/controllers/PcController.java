package practica.ControladorDispositivos.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.services.IGenericDispService;

@RestController
@RequestMapping("/pc")
@Tag(name = "Pc", description = "Dispositivos Pc almacenados con MAC conocida.")
public class PcController extends GenericDeviceController<PcDTO, Pc, String> {

    public PcController(IGenericDispService<PcDTO, Pc, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);
    }

    @Override
    protected String extractId(PcDTO pcDTO) {
        return pcDTO.getMacAddress();
    }

    @Override
    protected Class<Pc> getEntityClass() {
        return Pc.class;
    }
}
