package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.IIpService;
import practica.ControladorDispositivos.services.IMacAddressLogService;
import practica.ControladorDispositivos.services.MacsManager.CsvMacAddressProviderService;
import practica.ControladorDispositivos.services.MacsManager.IMacAddressProviderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/MacAddressProvider")
@Tag(name = "MacAddressProvier", description = "Operaciones relacionadas con la recepcion de datos sobre MACs conectadas")
public class MacAddressProviderController {

    private final IMacAddressProviderService csvProvider;
    private final IMacAddressProviderService txtProvider;
    private final IMacAddressLogService macAddressLogService;
    private final IIpService ipService;
    private final ModelMapper modelMapper;


    public MacAddressProviderController(@Qualifier("csvDataSource") IMacAddressProviderService macAddressProvider, @Qualifier("txt") IMacAddressProviderService txtProvider, @Qualifier("MacAddressLog") IMacAddressLogService macAddressLogService, @Qualifier("ip") IIpService ipService, ModelMapper modelMapper) {
        this.csvProvider = macAddressProvider;
        this.txtProvider = txtProvider;
        this.macAddressLogService = macAddressLogService;

        this.ipService = ipService;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/parser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Obtener los datos a partir de un archivo .csv y guardarlos en una base d datos", description = "Recibe un archivo .csv y obtiene de el los campos requeridos para guardarlos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo CSV recibido y procesado correctamente."),
            @ApiResponse(responseCode = "204", description = "Archivo CSV recibido correctamente, pero no se encontraron datos válidos para guardar."),
            @ApiResponse(responseCode = "400", description = "No se encuentra el archivo CSV."),
            @ApiResponse(responseCode = "500", description = "Error al procesar el archivo.")
    })
    public ResponseEntity<String> uploadFile(@Parameter(description = "Archivo .csv o .txt a subir", required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                                                    @RequestParam("file")MultipartFile file){

        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encuentra el archivo ");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = StringUtils.getFilenameExtension(filename);
        if (ext == null){
            return ResponseEntity.badRequest().body("Extensión no detectada");
        }

        IMacAddressProviderService svc;
        String label;
        switch (ext.toLowerCase()) {
            case "csv":
                svc   = csvProvider;
                label = "CSV";
                break;
            case "txt":
                svc   = txtProvider;
                label = "TXT";
                break;
            default:
                return ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("Formato no soportado: ." + ext);
        }

        try {
            List<MacAddressLog> listaLogs = svc.obtenerLogs(file);
            if (listaLogs.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            svc.guardarLogs(listaLogs);
            ipService.saveNewIpsByMac(listaLogs);
            return ResponseEntity.ok("Archivo " + label+ " procesado correctamente.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando el archivo " + label);
        }
    }

    @PutMapping("/addips")
    @Operation(summary = "Añade las ips a los dispositivos existentes", description = "A partir de los logs ya guardados busca ips no guardadas con igual mac")
    public ResponseEntity<?> addIps(){
        List<MacAddressLog> logsExistentes = macAddressLogService.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity,MacAddressLog.class))
                .toList();

        ipService.saveNewIpsByMac(logsExistentes);

        return ResponseEntity.ok("Ips actualizadas");
    }

}
