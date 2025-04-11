package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.models.dto.MacAddressLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.MacsManager.IMacComparatorService;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comparator")
@Tag(name = "Comparador de Macs", description = "Comparador de Macs")
public class MacComparatorController {
    private final IMacComparatorService macComparatorService;
    private final ModelMapper modelMapper;

    public MacComparatorController(IMacComparatorService macComparatorService, ModelMapper modelMapper) {
        this.macComparatorService = macComparatorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/logs")
    @Operation(summary = "Obtiene lista de MACs no registradas", description = "Compara las MACs recibidas de los Logs con las guardadas de dispositivos conocidos y devuelve las no registradas")
    public ResponseEntity<List<MacAddressLogDTO>> listaLogsNoCoincidente(){
        List<MacAddressLogDTO> listaLogs = macComparatorService.listaLogsNoCoincidentes().stream()
                .map(entity->modelMapper.map(entity,MacAddressLogDTO.class))
                .collect(Collectors.toList());

        if (!listaLogs.isEmpty()){
            return ResponseEntity.ok(listaLogs);
        }
        return ResponseEntity.notFound().build();
    }
}
