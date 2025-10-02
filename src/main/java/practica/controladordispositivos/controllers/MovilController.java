package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.services.IGenericDispService;


@RestController
@RequestMapping("/movil")
@Tag(name = "Movil", description = "Dispositivos moviles almacenados por MAC")
public class MovilController extends GenericDeviceController<MovilDTO, Movil, String> {


    public MovilController(IGenericDispService<MovilDTO, Movil, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);
    }

    @Override
    protected String extractId(MovilDTO movilDTO) {
        return movilDTO.getMacAddress();
    }

    @Override
    protected Class<Movil> getEntityClass() {
        return Movil.class;
    }
}