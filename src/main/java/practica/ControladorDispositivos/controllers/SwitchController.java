package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.SwitchDTO;
import practica.ControladorDispositivos.models.entities.Switch;
import practica.ControladorDispositivos.services.IGenericDispService;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/switch")
public class SwitchController {
    private final IGenericDispService<SwitchDTO,Switch,String> switchService;

    public SwitchController(@Qualifier("switch") IGenericDispService<SwitchDTO,Switch, String> switchService) {
        this.switchService = switchService;
    }

    @GetMapping
    public ResponseEntity<List<SwitchDTO>> listSwitch(){
        return ResponseEntity.ok(switchService.findAll());
    }



    @PutMapping("/{mac}")
    public ResponseEntity<?> updateSwitch(@RequestBody Switch updateSwitch, @PathVariable String mac){
        updateSwitch.setMacAddress(mac);
        Optional<SwitchDTO> switchOptional = switchService.update(updateSwitch);
        if (switchOptional.isPresent()){
            return ResponseEntity.ok(switchOptional.get());

        }
        return ResponseEntity.notFound().build();
    }


}
