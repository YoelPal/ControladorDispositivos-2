package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;

@RestController
@RequestMapping("/logs")
@Tag(name = "MacAddressLogs", description = "Logs con los datos recogidos ")
public class MacAddressLogController {

    private final IGenericDispService<MacAddressLogDTO, MacAddressLog,String> genericDispService;


    @Autowired
    public MacAddressLogController(@Qualifier("MacAddressLog") IGenericDispService<MacAddressLogDTO, MacAddressLog,String> genericDispService) {
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


}
