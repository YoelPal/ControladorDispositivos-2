package practica.controladordispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import practica.controladordispositivos.models.dto.DispositivoDTO;
import practica.controladordispositivos.models.dto.MovilDTO;
import practica.controladordispositivos.models.entities.Dispositivo;
import practica.controladordispositivos.models.entities.Movil;
import practica.controladordispositivos.services.IGenericDispService;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


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