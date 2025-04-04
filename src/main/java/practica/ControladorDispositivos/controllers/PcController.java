package practica.ControladorDispositivos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.services.impl.PcServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/pc")
public class PcController {
    private final PcServiceImpl pcService;

    public PcController(PcServiceImpl pcService) {
        this.pcService = pcService;
    }

    @GetMapping
    public List<Pc> listaPc(){
        return pcService.findAll();
    }

    @PostMapping
    public ResponseEntity<Pc> savePc(@RequestBody Pc pc){
        return ResponseEntity.ok(pcService.save(pc));
    }
}
