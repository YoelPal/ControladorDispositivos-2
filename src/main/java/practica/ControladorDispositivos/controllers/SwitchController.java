package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.SwitchDTO;
import practica.ControladorDispositivos.models.entities.Switch;
import practica.ControladorDispositivos.services.IGenericDispService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/switch")
@Tag(name = "Switch", description = "Dispositivos Switch almacenados con MAC conocida.")
public class SwitchController {
    private final IGenericDispService<SwitchDTO,Switch,String> switchService;
    private final ModelMapper modelMapper;

    public SwitchController(@Qualifier("switch") IGenericDispService<SwitchDTO,Switch, String> switchService, ModelMapper modelMapper) {
        this.switchService = switchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Switchs guardados", description = "Muestra una lista con todos los dispositivos Switch con MAC conocida guardados.")
    public ResponseEntity<List<SwitchDTO>> listSwitch(){
        List<SwitchDTO> listaSwitchs = switchService.findAll();
        if (listaSwitchs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(switchService.findAll());
    }

    @PostMapping
    @Operation(summary = "Guarda un Switch nuevo.", description = "Permite guardar un objeto Switch nuevo con su MAC")
    public ResponseEntity<?> saveSwitch(@Parameter(description = "Objeto Switch en formato JSON") @RequestBody SwitchDTO switchDTO){
        if (switchService.findById(switchDTO.getMacAddress()).isPresent()){
            return ResponseEntity.badRequest().body("La dirección MAC ya está asiganda.");
        }
        Switch switchA = modelMapper.map(switchDTO, Switch.class);
        return ResponseEntity.ok(switchService.save(switchA));
    }

    @PutMapping("/{macAddress}")
    @Operation(summary = "Actualiza un Switch existente.", description = "Permite actualizar un Switch existente a partir de un objeto JSON y una direccion MAC")
    public ResponseEntity<?> updateSwitch(@Parameter(description = "MAC Address del Switch a actualizar") @RequestBody SwitchDTO switchDto,
                                          @PathVariable String macAddress){
        if (!macAddress.equals(switchDto.getMacAddress())){
            return ResponseEntity.badRequest().body("El MAC Address en la URL no coincide con el MAC Address del cuerpo de la petición.");
        }

        Optional<SwitchDTO> switchOptional = switchService.findById(macAddress);
        if (switchOptional.isPresent()){
            switchDto.setMacAddress(macAddress);
            Switch updateSwitch = modelMapper.map(switchDto, Switch.class);
            return ResponseEntity.ok(switchService.update(updateSwitch));
        }
        return ResponseEntity.notFound().build();
    }

}
