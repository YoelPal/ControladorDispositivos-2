package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practica.ControladorDispositivos.models.dto.TabletDTO;
import practica.ControladorDispositivos.models.entities.Movil;
import practica.ControladorDispositivos.models.entities.Tablet;
import practica.ControladorDispositivos.services.IGenericDispService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tablet")
public class TabletController {
    private final IGenericDispService<TabletDTO,Tablet, String> tabletService;

    public TabletController(@Qualifier("tablet") IGenericDispService<TabletDTO,Tablet, String> tabletService) {
        this.tabletService = tabletService;
    }

    @GetMapping
    public ResponseEntity<List<TabletDTO>> listaTablet(){
       return ResponseEntity.ok(tabletService.findAll());
    }

    @PostMapping
    public ResponseEntity<TabletDTO> saveTablet(@RequestBody Tablet tablet){
        return ResponseEntity.ok(tabletService.save(tablet)) ;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTablet(@RequestBody Tablet tablet){
        Optional<TabletDTO> tabletOptional = tabletService.findById(tablet.getMacAddress());
        if(tabletOptional.isPresent()){
            return ResponseEntity.ok( tabletService.update(tablet));
        }
        return ResponseEntity.notFound().build();
    }
}
