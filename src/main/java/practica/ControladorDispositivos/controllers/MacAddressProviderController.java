package practica.ControladorDispositivos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.MacsProvider.MacAddressProviderService;

import java.util.List;

@RestController
@RequestMapping("/MacAddressProvider")
public class MacAddressProviderController {
    @Autowired
    private MacAddressProviderService macAddressProvider;


    @PostMapping("/csv")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encuentra el archivo CSV");
        }
        try {
            List<MacAddressLog> listaLogs = macAddressProvider.obtenerMacsOpenCSV(file);
            if (!listaLogs.isEmpty()){
                macAddressProvider.guardarDispositivos(listaLogs);
                return ResponseEntity.ok().body("Archivo CSV recibido y guardado correctamente");
            }else {
                return ResponseEntity.ok().body("Archivo CSV recibido correctamente, pero no se encontraron datos válidos para guardar.");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo.");
        }
    }
}
