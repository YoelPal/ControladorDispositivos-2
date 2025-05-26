package practica.ControladorDispositivos.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.services.IGenericDispService;
import practica.ControladorDispositivos.services.IIpService;
import practica.ControladorDispositivos.services.IMacAddressLogService;
import practica.ControladorDispositivos.services.MacsManager.CsvMacAddressProviderService;
import practica.ControladorDispositivos.services.MacsManager.IMacAddressProviderService;
import practica.ControladorDispositivos.services.MacsManager.IMacComparatorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/MacAddressProvider")
@Tag(name = "MacAddressProvier", description = "Operaciones relacionadas con la recepcion de datos sobre MACs conectadas")
public class MacAddressProviderController {
    private final IGenericDispService<DispositivoDTO, Dispositivo,String> dispService;
    private final IMacAddressProviderService csvProvider;
    private final IMacAddressProviderService txtProvider;
    private final IMacAddressLogService macAddressLogService;
    private final IIpService ipService;
    private final IMacComparatorService macComparatorService;
    private final ModelMapper modelMapper;


    public MacAddressProviderController(@Qualifier("dispositivo") IGenericDispService<DispositivoDTO, Dispositivo, String> dispService, @Qualifier("csvDataSource") IMacAddressProviderService macAddressProvider, @Qualifier("txt") IMacAddressProviderService txtProvider, @Qualifier("MacAddressLog") IMacAddressLogService macAddressLogService, @Qualifier("ip") IIpService ipService, IMacComparatorService macComparatorService, ModelMapper modelMapper) {
        this.dispService = dispService;
        this.csvProvider = macAddressProvider;
        this.txtProvider = txtProvider;
        this.macAddressLogService = macAddressLogService;

        this.ipService = ipService;
        this.macComparatorService = macComparatorService;
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
    public ResponseEntity<Map<String,Object>> uploadFile(@Parameter(description = "Archivo .csv o .txt a subir", required = true,
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                                                    @RequestParam("file")MultipartFile file){

        Map<String,Object> response = new HashMap<>();
        if (file.isEmpty()){
            response.put("message","No se encuentra el archivo ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = StringUtils.getFilenameExtension(filename);


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
                response.put("message","Formato no soportado: ." + ext);
                return ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(response);
        }

        try {
            List<MacAddressLog> listaLogs = svc.obtenerLogs(file);
            if (listaLogs.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<MacAddressLog> listaNoCoincidentes = new ArrayList<>();
            List<MacAddressLog> listadiferenteIp = new ArrayList<>();
            for (MacAddressLog log : listaLogs){
                if (dispService.findById(log.getMacAddress()).isEmpty()){
                    listaNoCoincidentes.add(log);
                }
                else {
                    String ip = log.getIp();
                    if (ipService.findByIpAddress(ip).isEmpty()){
                        listadiferenteIp.add(log);
                    }
                }
            }



            svc.guardarLogs(listaNoCoincidentes);
            ipService.saveNewIpsByMac(listadiferenteIp);



            if (!listaNoCoincidentes.isEmpty()){
                List<String> macsNoRegistradas = listaNoCoincidentes.stream()
                                .map(MacAddressLog::getMacAddress)
                                        .toList();
                response.put("unregisteredMacs",macsNoRegistradas);
                log.warn("ATENCION: Se detectaron {} MACs no registradas.",listaNoCoincidentes.size());

            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","Error procesando el archivo " + label);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
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
