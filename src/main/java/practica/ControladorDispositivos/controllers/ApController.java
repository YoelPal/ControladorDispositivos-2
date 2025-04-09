package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.ApDTO;
import practica.ControladorDispositivos.models.entities.Ap;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;

@RestController
@RequestMapping("/ap")
public class ApController {
    private final IGenericDispService<ApDTO,Ap,String> apService;

    public ApController(@Qualifier("ap") IGenericDispService<ApDTO, Ap, String> apService) {
        this.apService = apService;
    }

    @GetMapping
    public ResponseEntity<List<ApDTO>> listaAp(){
        return ResponseEntity.ok(apService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> saveAp(@RequestBody Ap ap){
        return ResponseEntity.ok(apService.save(ap));
    }

    @PutMapping
    public ResponseEntity<?> updateAp(@RequestBody Ap ap){
        return ResponseEntity.ok(apService.update(ap));
    }
}
