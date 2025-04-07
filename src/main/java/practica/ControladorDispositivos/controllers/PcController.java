package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.impl.PcServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/pc")
public class PcController {
    private final IGenericDispService<Pc,String> pcService;

    public PcController(@Qualifier("Pc") IGenericDispService<Pc,String> pcService) {
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
