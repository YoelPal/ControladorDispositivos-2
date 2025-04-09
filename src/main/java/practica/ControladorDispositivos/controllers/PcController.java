package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.PcDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Pc;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.impl.PcServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pc")
public class PcController {
    private final IGenericDispService<PcDTO,Pc,String> pcService;

    public PcController(@Qualifier("Pc") IGenericDispService<PcDTO,Pc,String> pcService) {
        this.pcService = pcService;
    }

    @GetMapping
    public List<PcDTO> listaPc(){
        return pcService.findAll();
    }

    @PostMapping
    public ResponseEntity<PcDTO> savePc(@RequestBody Pc pc){
        return ResponseEntity.ok(pcService.save(pc));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePc(@RequestBody Pc pc){
        Optional<PcDTO> pcOptional = pcService.findById(pc.getMacAddress());
        if (pcOptional.isPresent()){
            return ResponseEntity.ok(pcService.update(pc));
        }
        return ResponseEntity.notFound().build();
    }
}
