package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.services.impl.MovilServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/moviles")
public class MovilController {
    @Autowired
    private MovilServiceImpl movilService;

    public MovilController(MovilServiceImpl movilService) {
        this.movilService = movilService;
    }

    @GetMapping
    public List<Movil> listaMoviles() {
        return movilService.findAll();
    }

    @PostMapping
    public ResponseEntity<Movil> saveMovil(@RequestBody Movil movil){
       return ResponseEntity.ok(movilService.save(movil));
    }
}