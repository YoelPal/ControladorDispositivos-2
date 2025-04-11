package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.TabletDTO;
import practica.ControladorDispositivos.models.entities.Tablet;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tablet")
@Tag(name = "Tablets", description = "Dispositivos Tablet almacenados con MAC conocida.")
public class TabletController {
    private final IGenericDispService<TabletDTO,Tablet, String> tabletService;
    private final ModelMapper modelMapper;

    public TabletController(@Qualifier("tablet") IGenericDispService<TabletDTO,Tablet, String> tabletService, ModelMapper modelMapper) {
        this.tabletService = tabletService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Tablets guardados", description = "Muestra una lista con todos los dispositivos Tablet con MAC conocida guardados.")
    public ResponseEntity<List<TabletDTO>> listaTablet(){
        List<TabletDTO> listaTabets = tabletService.findAll();
        if (listaTabets.isEmpty()){
            return ResponseEntity.noContent().build();
        }
       return ResponseEntity.ok(tabletService.findAll());
    }

    @PostMapping
    @Operation(summary = "Guarda una Tablet nueva.", description = "Permite guardar un objeto Tablet nuevo con su MAC")
    public ResponseEntity<?> saveTablet(@Parameter(description = "Objeto Tablet en formato JSON") @RequestBody TabletDTO tabletDTO){
        if (tabletService.findById(tabletDTO.getMacAddress()).isPresent()){
            return ResponseEntity.badRequest().body("La dirección MAC ya está asignada");
        }
        Tablet tablet = modelMapper.map(tabletDTO, Tablet.class);
        return ResponseEntity.ok(tabletService.save(tablet)) ;
    }

    @PutMapping("/{macAddress}")
    @Operation(summary = "Actualiza una Tablet existente.", description = "Permite actualizar un objeto Tablet ya existente.")
    public ResponseEntity<?> updateTablet(@Parameter(description = "Objeto Tablet en formato JASON") @RequestBody TabletDTO tabletDTO,
                                          @Parameter(description = "Dirección Mac de la Tablet a actualizar")@PathVariable String macAddress){
        if (!macAddress.equals(tabletDTO.getMacAddress())){
            return ResponseEntity.badRequest().body("El MAC Address en la URL no coincide con el MAC Address del cuerpo de la petición.");
        }
        Optional<TabletDTO> tabletOptional = tabletService.findById(tabletDTO.getMacAddress());
        if(tabletOptional.isPresent()){
            tabletDTO.setMacAddress(macAddress);
            Tablet tablet = modelMapper.map(tabletDTO, Tablet.class);
            return ResponseEntity.ok(tabletService.update(tablet));
        }
        return ResponseEntity.notFound().build();
    }

}
