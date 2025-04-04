package practica.ControladorDispositivos.services.MacsProvider;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.dto.DispositivoDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvParserService  {
    private static final String CSV_SEPARATOR = ",";
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParserService.class);

    @Autowired
    private MacAddressLogRepository macAddressLogRepository;


    public List<DispositivoDTO> obtenerMacs(MultipartFile file) {
        List<DispositivoDTO> dispositivos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String linea;
            int lineNumber = 1;
            boolean isHeader = true;

            while ((linea = reader.readLine()) != null) {
                lineNumber++;
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] valores = linea.split(CSV_SEPARATOR);
                if (valores.length >= 4) {
                    try {
                        DispositivoDTO dto = new DispositivoDTO();
                        dto.setEmpresa(valores[0].trim());
                        dto.setSede(valores[1].trim());
                        dto.setMacAddress(valores[2].trim());
                        try {
                            dto.setTimestamp(LocalDateTime.parse(valores[3].trim()));
                        } catch (DateTimeParseException e) {
                            throw new RuntimeException(e);
                        }
                        dispositivos.add(dto);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Esto podría ocurrir si split() produce menos campos de los esperados
                        //LOGGER.warning("Error de índice en línea " + lineNumber + ": No se encontraron todos los campos esperados después de split. Línea: '" + line + "'");
                    } catch (Exception e) {
                        // Capturar otros errores al procesar la fila
                        //LOGGER.log(Level.SEVERE, "Error procesando la línea " + lineNumber + ": '" + line + "'", e);
                        // Decide si continuar o detener
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dispositivos;
    }


    public List<MacAddressLog> obtenerMacsOpenCSV(MultipartFile file) {

        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ISO_LOCAL_DATE_TIME, // Formato con 'T'
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // Formato con espacio
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), // Formato con milisegundos y 'Z'
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"), // Formato con espacio y milisegundos
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") // Otro formato posible
        );
        List<MacAddressLog> listaDispositivos = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)//--Para saltar la linea de la cabecera--
                    .build()) {
            String[] datos;
            int rowNumber = 0;

            while ((datos = csvReader.readNext())!=null){
                rowNumber++;
                MacAddressLog macAddressLog = null;
                if (datos.length>=4){
                    try {


                        /*DispositivoDTO dto = new DispositivoDTO();
                        dto.setEmpresa(datos[0].trim());
                        dto.setSede(datos[1].trim());
                        dto.setMacAddress(datos[2].trim());*/

                        String timestampStr = datos[3].trim();
                        LocalDateTime parsedTimestamp = null;
                        for(DateTimeFormatter format:formatters){
                            try {
                                parsedTimestamp = LocalDateTime.parse(timestampStr,format);
                                break;
                            }catch (DateTimeParseException e){

                            }
                        }
                        if (parsedTimestamp != null){

                            macAddressLog = MacAddressLog.builder()
                                    .empresa(datos[0].trim())
                                    .departamento(datos[1].trim())
                                    .macAddress(datos[2].trim())
                                    .timestamp(parsedTimestamp)
                                    .build();
                            //dto.setTimestamp(parsedTimestamp);
                        }else {
                            LOGGER.warn("Línea {}: Formato de timestamp inválido ('{}'). Se establece como null.", rowNumber, timestampStr);
                            macAddressLog = MacAddressLog.builder()
                                    .empresa(datos[0].trim())
                                    .departamento(datos[1].trim())
                                    .macAddress(datos[2].trim())
                                    .timestamp(null)
                                    .build();
                        }

                        listaDispositivos.add(macAddressLog);
                    } catch (Exception e) {
                        LOGGER.error("Línea {}: Error inesperado procesando datos '{}'. Fila ignorada.", rowNumber, String.join(",", datos), e);
                    }
                }else {
                    LOGGER.warn("Línea {}: Se ignoró por tener {} columnas en lugar de las 4 esperadas. Contenido: '{}'", rowNumber, datos.length, String.join(",", datos));
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return listaDispositivos;
    }

    public void guardarDispositivos(List<MacAddressLog> macAddressLogs){
        macAddressLogRepository.saveAll(macAddressLogs);
    }
}
