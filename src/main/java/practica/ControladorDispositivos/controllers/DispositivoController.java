package practica.ControladorDispositivos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.services.impl.DispositivoServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {
    private final DispositivoServiceImpl dispositivoService;

    public DispositivoController(DispositivoServiceImpl dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @GetMapping
    public List<Dispositivo> listaDispoitivos (){
        return (List<Dispositivo>) dispositivoService.findAll();
    }

    @DeleteMapping("/{macAddress}")
    public ResponseEntity<?> deleteDispositivo(@PathVariable(value = "macAddress")String mac){
        if (dispositivoService.deleteByMac(mac)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createDispositivo(@RequestBody Dispositivo dispositivo){
        if (dispositivoService.findByMac(dispositivo.getMacAddress()).isPresent()){
            return ResponseEntity.badRequest().body("La Mac ya existe");
        }
        return ResponseEntity.ok(dispositivoService.save(dispositivo));
    }



}
