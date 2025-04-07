package practica.ControladorDispositivos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.MacsProvider.IMacComparatorService;



import java.util.List;

@RestController
@RequestMapping("/comparator")
public class MacComparatorController {
    private final IMacComparatorService macComparatorService;

    public MacComparatorController(IMacComparatorService macComparatorService) {
        this.macComparatorService = macComparatorService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<MacAddressLog>> listaLogsNoCoincidente(){
        List<MacAddressLog> listaLogs = macComparatorService.listaLogsNoCoincidentes();
        if (!listaLogs.isEmpty()){
            return ResponseEntity.ok(listaLogs);
        }
        return ResponseEntity.notFound().build();
    }
}
