package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {
    private final IGenericDispService<Dispositivo,String> genericDispService;

    public DispositivoController(@Qualifier("Dispositivo") IGenericDispService<Dispositivo,String> dispositivoService) {
        this.genericDispService = dispositivoService;
    }

    @GetMapping
    public List<Dispositivo> listaDispositivos(){
        return  genericDispService.findAll();
    }

    @DeleteMapping("/{macAddress}")
    public ResponseEntity<?> deleteDispositivo(@PathVariable(value = "macAddress")String mac){
        if (genericDispService.deleteById(mac)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createDispositivo(@RequestBody Dispositivo dispositivo){
        if (genericDispService.findById(dispositivo.getMacAddress()).isPresent()){
            return ResponseEntity.badRequest().body("La dirección Mac ya existe");
        }
        return ResponseEntity.ok(genericDispService.save(dispositivo));
    }



}
