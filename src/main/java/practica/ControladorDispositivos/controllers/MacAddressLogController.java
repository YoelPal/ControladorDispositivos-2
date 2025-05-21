package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.IMacAddressLogService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "MacAddressLogs", description = "Logs con los datos recogidos ")
public class MacAddressLogController {

    private final IMacAddressLogService genericDispService;
  


    public MacAddressLogController(@Qualifier("MacAddressLog") IMacAddressLogService genericDispService,ModelMapper modelMapper) {
        this.genericDispService = genericDispService;
      
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Logs guardados.", description = "Devuelve la lista de todos los logs que se han recibido.")
    public ResponseEntity<List<MacAddressLogDTO>> findAll(){
        List<MacAddressLogDTO> listaLogs = genericDispService.findAll();
        if (listaLogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaLogs);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Obtener lista de Logs guardados.", description = "Devuelve la lista de todos los logs que se han recibido.")
    public ResponseEntity<Page<MacAddressLogDTO>> findAllPaginated(Pageable pageable,
                                                                   @RequestParam(value = "macAddress", required = false) String macAddress,
                                                                   @RequestParam(value = "sede", required = false) String sede,
                                                                   @RequestParam(value = "noCoincidentes", required = false) Boolean noCoincidentes){
        Page<MacAddressLogDTO> listaLogs = genericDispService.findAllPaginated(pageable,macAddress,sede,noCoincidentes);
        if (listaLogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaLogs);
    }

    @GetMapping("/{sede}")
    @Operation(summary = "Obtener Logs por sede",description = "Muestra una lista de Logs filtrados por el nombre de la sede.")
    public ResponseEntity<List<MacAddressLogDTO>> findBySede(@Parameter(description = "Nombre de la sede de los logs que se desean encontrar")@PathVariable String sede ){
        Optional<List<MacAddressLogDTO>> listaLogsSede = genericDispService.findBySede(sede);
        return listaLogsSede.map(macAddressLogDTOS -> ResponseEntity.ok().body(macAddressLogDTOS)).orElseGet(() -> ResponseEntity.noContent().build());
    }


}
