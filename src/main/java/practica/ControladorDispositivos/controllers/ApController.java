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
import practica.ControladorDispositivos.models.dto.ApDTO;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ap")
@Tag(name = "Ap", description = "Dispositivos Ap almacenados con Mac conocida")
public class ApController {
    private final IGenericDispService<ApDTO,Ap,String> apService;
    private final ModelMapper modelMapper;
    private final IGenericDispService<DispositivoDTO, Dispositivo,String> dispService;

    public ApController(@Qualifier("ap") IGenericDispService<ApDTO, Ap, String> apService, ModelMapper modelMapper,@Qualifier("dispositivo") IGenericDispService<DispositivoDTO, Dispositivo, String> dispService) {
        this.apService = apService;
        this.modelMapper = modelMapper;
        this.dispService = dispService;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Aps guardadas", description = "Devuelve una lista con las Aps con dirección MAC conocida.")
    public ResponseEntity<List<ApDTO>> listaAp(){
        List<ApDTO> listaAp = apService.findAll();
        if (listaAp.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaAp);
    }

    @PostMapping
    @Operation(summary = "Guarda un nuevo dispositivo Ap", description = "Guarda un dispositivo Ap nuevo con su dirección MAC. ")
    public ResponseEntity<?> saveAp(@Parameter(description = "Objeto Ap en formato JSON.") @RequestBody ApDTO apDTO){
        if (dispService.findById(apDTO.getMacAddress()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La dirección MAC ya está asignada");
        }
        Ap ap = modelMapper.map(apDTO,Ap.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(apService.save(ap));
    }

    @PutMapping("/{macAddress}")
    @Operation(summary = "Actualiza un dispositivo Ap existente", description = "Actualiza un dispositivo Ap a partir de una MAC Address y el objeto en formato JASON.")
    public ResponseEntity<?> updateAp(@Parameter(description = "Mac Address del Ap a actualizar") @PathVariable String macAddress,
                                      @Parameter(description = "Objeto Ap con los datos a actualizar en formato JSON") @RequestBody ApDTO apDTO){
        if (!macAddress.equals(apDTO.getMacAddress())){
            return ResponseEntity.badRequest().body("El MAC Address en la URL no coincide con el MAC Address del cuerpo de la petición.");
        }
        Optional<ApDTO> apDTOOptional = apService.findById(apDTO.getMacAddress());
        if (apDTOOptional.isPresent()){
            apDTO.setMacAddress(macAddress);
            Ap ap = modelMapper.map(apDTO,Ap.class);
            return ResponseEntity.ok(apService.update(ap));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ap no encontrado con la MAC: " + macAddress);
    }

    @GetMapping("/sede/{sede}")
    @Operation(summary = "Busca Ap por su sede", description = "Muestra una lista de Aps con la misma sede.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "404", description = "Aps no encontrados con esa sede."),
            @ApiResponse(responseCode = "200", description = "Lista encontrada.")
    })
    public ResponseEntity<List<ApDTO>> findBySede(@Parameter(description = "Sede de los dispositivos")@PathVariable String sede){
        Optional<List<ApDTO>> dispositivoDTOList = apService.findBySede(sede);
        if (dispositivoDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dispositivoDTOList.get());
    }
}
