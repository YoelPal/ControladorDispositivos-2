package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.IGenericDispService;


@RestController
@RequestMapping("/dispositivos")
@Tag(name = "Dispositivos",description = "Dispositivos almacenados con MAC conocida")
public class DispositivoController extends GenericDeviceController<DispositivoDTO,Dispositivo,String> {


    public DispositivoController(IGenericDispService<DispositivoDTO, Dispositivo, String> tipoService, IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService, ModelMapper mapper) {
        super(tipoService, dispositivoService, mapper);

    }


    @DeleteMapping("/{macAddress}")
    @Operation(summary = "Eliminar dispositivo ", description = "Elimina un dispositivo(de cualquier tipo) a partir de su MAC")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado con esa MAC. ",content = @Content(mediaType = "dispositivo/json")),
                    @ApiResponse(responseCode = "200", description = "Producto eliminado")
    })
    public ResponseEntity<?> deleteDispositivo(@Parameter(description = "MAC del dispositivo a eliminar") @PathVariable(value = "macAddress")String mac){
        if (dispositivoService.deleteById(mac)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    protected String extractId(DispositivoDTO dispositivoDTO) {
        return dispositivoDTO.getMacAddress();
    }

    @Override
    protected Class<Dispositivo> getEntityClass() {
        return Dispositivo.class;
    }


}
