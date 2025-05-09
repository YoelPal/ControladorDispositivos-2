package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.MacsManager.CsvMacAddressProviderService;
import practica.ControladorDispositivos.services.MacsManager.IMacAddressProviderService;
import java.util.List;

@RestController
@RequestMapping("/MacAddressProvider")
@Tag(name = "MacAddressProvier", description = "Operaciones relacionadas con la recepcion de datos sobre MACs conectadas")
public class MacAddressProviderController {

    private final IMacAddressProviderService macAddressProvider;

    
    public MacAddressProviderController(@Qualifier("csvDataSource") IMacAddressProviderService macAddressProvider) {
        this.macAddressProvider = macAddressProvider;

    }


    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Obtener los datos a partir de un archivo .csv y guardarlos en una base d datos", description = "Recibe un archivo .csv y obtiene de el los campos requeridos para guardarlos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo CSV recibido y procesado correctamente."),
            @ApiResponse(responseCode = "204", description = "Archivo CSV recibido correctamente, pero no se encontraron datos válidos para guardar."),
            @ApiResponse(responseCode = "400", description = "No se encuentra el archivo CSV."),
            @ApiResponse(responseCode = "500", description = "Error al procesar el archivo.")
    })
    public ResponseEntity<String> uploadCsvFile(@Parameter(description = "Archivo .csv a subir", required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                                                    @RequestParam("file")MultipartFile file){
        if (macAddressProvider instanceof CsvMacAddressProviderService){
            ((CsvMacAddressProviderService)macAddressProvider).setFile(file);
        }
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encuentra el archivo CSV");
        }
        try {
            List<MacAddressLog> listaLogs = macAddressProvider.obtenerLogs();
            if (!listaLogs.isEmpty()){
                macAddressProvider.guardarLogs(listaLogs);
                return ResponseEntity.ok().body("Archivo CSV recibido y guardado correctamente");
            }else {
                return ResponseEntity.noContent().build();
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo.");
        }
    }
}
