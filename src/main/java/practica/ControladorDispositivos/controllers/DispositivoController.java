package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dispositivos")
@Tag(name = "Dispositivos",description = "Dispositivos almacenados con MAC conocida")
public class DispositivoController {
    private final IGenericDispService<DispositivoDTO, Dispositivo, String> genericDispService;


    public DispositivoController(@Qualifier("dispositivo") IGenericDispService<DispositivoDTO, Dispositivo, String> dispositivoService) {
        this.genericDispService = dispositivoService;

    }

    @GetMapping
    @Operation(summary = "Obtener lista de dispositivos guardados", description = "Devuelve la lista de dispositivos guardados por MAC.")
    public ResponseEntity<List<DispositivoDTO>> listaDispositivos() {
        List<DispositivoDTO> listaDispositivos = genericDispService.findAll();
        if (listaDispositivos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaDispositivos);
    }

    @GetMapping(value = "/{mac}")
    @Operation(summary = "Obtiene un dispositivo por su MAC")
    public ResponseEntity<Optional<DispositivoDTO>> findByMAc(@Parameter(description = "Dirección MAC del dispositivo que se desea encontrar.") @PathVariable String mac){
        if (genericDispService.findById(mac).isPresent()){
            return ResponseEntity.ok(genericDispService.findById(mac));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{macAddress}")
    @Operation(summary = "Eliminar dispositivo ", description = "Elimina un dispositivo(de cualquier tipo) a partir de su MAC")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado con esa MAC. ",content = @Content(mediaType = "dispositivo/json")),
                    @ApiResponse(responseCode = "200", description = "Producto eliminado")
    })
    public ResponseEntity<?> deleteDispositivo(@Parameter(description = "MAC del dispositivo a eliminar") @PathVariable(value = "macAddress")String mac){
        if (genericDispService.deleteById(mac)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sede/{sede}")
    @Operation(summary = "Busca dispositivo por su sede", description = "Muestra una lista de dispositivos con la misma sede.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "404", description = "Dispositivos no enconstrados con esa sede."),
            @ApiResponse(responseCode = "200", description = "Lista encontrada.")
    })
    public ResponseEntity<List<DispositivoDTO>> findBySede(@Parameter(description = "Sede de los dispositivos")@PathVariable String sede){
        Optional<List<DispositivoDTO>> dispositivoDTOList = genericDispService.findBySede(sede);
        if (dispositivoDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dispositivoDTOList.get());
    }



}
