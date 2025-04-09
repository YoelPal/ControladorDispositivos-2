package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.MovilDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.impl.MovilServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviles")
public class MovilController {

    private final IGenericDispService<MovilDTO,Movil,String> movilService;

    public MovilController(@Qualifier("Movil") IGenericDispService<MovilDTO,Movil,String> movilService) {
        this.movilService = movilService;
    }

    @GetMapping
    public List<MovilDTO> listaMoviles() {
        return movilService.findAll();
    }

    @PostMapping
    public ResponseEntity<MovilDTO> saveMovil(@RequestBody Movil movil){
       return ResponseEntity.ok(movilService.save(movil));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateMovil(@RequestBody Movil movil){
        Optional<MovilDTO> movilOptional = movilService.findById(movil.getMacAddress());
        if (movilOptional.isPresent()){
            return ResponseEntity.ok( movilService.update(movil));
        }
        return ResponseEntity.notFound().build();
    }
}