package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movil")
@Tag(name = "Movil", description = "Dispositivos moviles almacenados por MAC")
public class MovilController {

    private final IGenericDispService<MovilDTO,Movil,String> movilService;
    private final ModelMapper modelMapper;

    public MovilController(@Qualifier("Movil") IGenericDispService<MovilDTO,Movil,String> movilService, ModelMapper modelMapper) {
        this.movilService = movilService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de móviles", description = "Muestra la lista de dispositivos moviles almacenados por MAC.")
    public ResponseEntity<List<MovilDTO>> listaMoviles() {
        List<MovilDTO> listaMoviles = movilService.findAll();
        if (listaMoviles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaMoviles);    }

    @PostMapping
    @Operation(summary = "Guarda un dispositivo movil", description = "Guarda un dispositivo móvil a partir de un archivo JASON")
    public ResponseEntity<?> saveMovil(@Parameter(description = "Objeto Movil en formato JASON.") @RequestBody MovilDTO movilDTO){
        Optional<MovilDTO> movilDTOOptional = movilService.findById(movilDTO.getMacAddress());
        if (movilDTOOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La dirección MAC ya está asignada");
        }
        Movil movil = modelMapper.map(movilDTO,Movil.class);
       return ResponseEntity.status(HttpStatus.CREATED).body(movilService.save(movil));
    }

    @PutMapping("/{macAddress}")
    @Operation(summary = "Actualiza un dispositivo movil", description = "Permite actualizar un objeto Movil ya existente")
    public ResponseEntity<?> updateMovil(@Parameter(description = "Objeto Movil con los datos a actualizar en formato JASON.") @RequestBody MovilDTO movilDTO,
                                         @Parameter(description = "Direccion MAc del Movil a actualizar")@PathVariable String macAddress){
        if (!macAddress.equals(movilDTO.getMacAddress())){
            return ResponseEntity.badRequest().body("El MAC Address en la URL no coincide con el MAC Address del cuerpo de la petición.");
        }
        Optional<MovilDTO> movilOptional = movilService.findById(movilDTO.getMacAddress());
        if (movilOptional.isPresent()){
            movilDTO.setMacAddress(macAddress);
            Movil movil = modelMapper.map(movilDTO, Movil.class);
            return ResponseEntity.ok( movilService.update(movil));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sede/{sede}")
    @Operation(summary = "Busca Moviles por su sede", description = "Muestra una lista de moviles con la misma sede.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "404", description = "Moviles no enconstrados con esa sede."),
            @ApiResponse(responseCode = "200", description = "Lista encontrada.")
    })
    public ResponseEntity<List<MovilDTO>> findBySede(@Parameter(description = "Sede de los moviles")@PathVariable String sede){
        Optional<List<MovilDTO>> dispositivoDTOList = movilService.findBySede(sede);
        if (dispositivoDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dispositivoDTOList.get());
    }
}