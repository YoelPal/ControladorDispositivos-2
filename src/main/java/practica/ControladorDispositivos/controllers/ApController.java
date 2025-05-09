package practica.ControladorDispositivos.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.ApDTO;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.IGenericDispService;


@RestController
@RequestMapping("/ap")
@Tag(name = "Ap", description = "Dispositivos Ap almacenados con Mac conocida")
public class ApController extends GenericDeviceController<ApDTO,Ap,String>{


    public ApController(@Qualifier("ap") IGenericDispService<ApDTO, Ap, String> apService,@Qualifier("dispositivo") IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(apService, dispositivoService, mapper);
    }


    @Override
    protected String extractId(ApDTO apDTO) {
        return apDTO.getMacAddress();
    }

    @Override
    protected Class<Ap> getEntityClass() {
        return Ap.class;
    }


}
