package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.SwitchDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.Switch;
import practica.ControladorDispositivos.services.IGenericDispService;


@RestController
@RequestMapping("/switch")
@Tag(name = "Switch", description = "Dispositivos Switch almacenados con MAC conocida.")
public class SwitchController extends GenericDeviceController<SwitchDTO, Switch, String>{


    public SwitchController(IGenericDispService<SwitchDTO, Switch, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);
    }

    @Override
    protected String extractId(SwitchDTO switchDTO) {
        return switchDTO.getMacAddress();
    }

    @Override
    protected Class<Switch> getEntityClass() {
        return Switch.class;
    }
}
