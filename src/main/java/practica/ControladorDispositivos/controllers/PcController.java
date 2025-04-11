package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pc")
@Tag(name = "Pc", description = "Dispositivos Pc almacenados con MAC conocida.")
public class PcController {
    private final IGenericDispService<PcDTO,Pc,String> pcService;
    private final ModelMapper modelMapper;

    public PcController(@Qualifier("Pc") IGenericDispService<PcDTO,Pc,String> pcService, ModelMapper modelMapper) {
        this.pcService = pcService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Pcs guardados", description = "Muestra una lista con todos los dispositivos Pc con MAC conocida guardados.")
    public ResponseEntity<List<PcDTO>> listaPCs(){
        List<PcDTO> listaPcs = pcService.findAll();
        if (listaPcs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaPcs);
    }

    @PostMapping
    @Operation(summary = "Guarda un Pc nuevo.", description = "Permite guardar un objeto Pc nuevo con su MAC")
    public ResponseEntity<?> savePc(@Parameter(description = "Objeto PC en formato JSON")@RequestBody PcDTO pcDTO){
        if (pcService.findById(pcDTO.getMacAddress()).isPresent()){
            return ResponseEntity.badRequest().body("La dirección MAC ya está asignada");
        }
        Pc pc = modelMapper.map(pcDTO, Pc.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(pcService.save(pc));
    }

    @PutMapping("/{macAddress}")
    @Operation(summary = "Actualiza un Pc existente.", description = "Permite actualizar un objeto Pc ya existente.")
    public ResponseEntity<?> updatePc(@Parameter(description = "Mac Address del Pc a actualizar") @PathVariable String macAddress,
                                      @Parameter(description = "Objeto Pc con los datos a actualizar en formato JSON") @RequestBody PcDTO pcDTO){
        if (!macAddress.equals(pcDTO.getMacAddress())){
            return ResponseEntity.badRequest().body("El MAC Address en la URL no coincide con el MAC Address del cuerpo de la petición.");
        }
        Optional<PcDTO> pcOptional = pcService.findById(pcDTO.getMacAddress());
        if (pcOptional.isPresent()){
            pcDTO.setMacAddress(macAddress);
            Pc pc = modelMapper.map(pcDTO,Pc.class);
            return ResponseEntity.ok(pcService.update(pc));
        }
        return ResponseEntity.notFound().build();
    }
}
