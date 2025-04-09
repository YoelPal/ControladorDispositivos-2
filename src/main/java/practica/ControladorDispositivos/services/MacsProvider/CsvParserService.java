package practica.ControladorDispositivos.services.MacsProvider;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.dto.DispositivoLogDTO;
import practica.ControladorDispositivos.models.entities.MacAddressLog;
import practica.ControladorDispositivos.models.repositories.MacAddressLogRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CsvParserService {
    private static final String CSV_SEPARATOR = ",";
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParserService.class);
    private MacAddressLogRepository macAddressLogRepository;

    @Value("${csv.header.empresa}")
    private String headerEmpresaName;

    @Value("${csv.header.departamento}")
    private String headerDepartamentoName;

    @Value("${csv.header.mac}")
    private String headerMacName;

    @Value("${csv.header.timestamp}")
    private String headerTimestampName;


    @Value("#{'${cabeceras.requeridas}'.split(',')}")
    private List<String> cabecerasRequeridas;

    List<DateTimeFormatter> formatters = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME, // Formato con 'T'
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // Formato con espacio
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), // Formato con milisegundos y 'Z'
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"), // Formato con espacio y milisegundos
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") // Otro formato posible
    );


    public CsvParserService() {
    }


    public List<DispositivoLogDTO> obtenerMacs(MultipartFile file) {
        List<DispositivoLogDTO> dispositivos = new ArrayList<>();
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
                        DispositivoLogDTO dto = new DispositivoLogDTO();
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


        List<MacAddressLog> listaDispositivos = new ArrayList<>();

        char separator = '|';

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(separator)
                    .build();


            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)//--Para saltar la linea de la cabecera--
                    .build();


            String[] datos;
            int rowNumber = 0;

            while ((datos = csvReader.readNext()) != null) {
                rowNumber++;
                MacAddressLog macAddressLog = null;
                if (datos.length >= 4) {
                    try {


                        /*DispositivoDTO dto = new DispositivoDTO();
                        dto.setEmpresa(datos[0].trim());
                        dto.setSede(datos[1].trim());
                        dto.setMacAddress(datos[2].trim());*/

                        String timestampStr = datos[3].trim();
                        LocalDateTime parsedTimestamp = null;
                        for (DateTimeFormatter format : formatters) {
                            try {
                                parsedTimestamp = LocalDateTime.parse(timestampStr, format);
                                break;
                            } catch (DateTimeParseException e) {

                            }
                        }
                        if (parsedTimestamp != null) {

                            macAddressLog = MacAddressLog.builder()
                                    .empresa(datos[0].trim())
                                    .departamento(datos[1].trim())
                                    .macAddress(datos[2].trim())
                                    .timestamp(parsedTimestamp)
                                    .build();
                            //dto.setTimestamp(parsedTimestamp);
                        } else {
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
                } else {
                    LOGGER.warn("Línea {}: Se ignoró por tener {} columnas en lugar de las 4 esperadas. Contenido: '{}'", rowNumber, datos.length, String.join(",", datos));
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return listaDispositivos;
    }

    public void guardarDispositivos(List<MacAddressLog> macAddressLogs) {
        macAddressLogRepository.saveAll(macAddressLogs);
    }

    public List<MacAddressLog> CsvValoresPorCabeceras(MultipartFile file) {
        char separator = ',';
        Map<String, Integer> cabecerasMap = new HashMap<>();
        List<MacAddressLog> macAddressLogs = new ArrayList<>();



        try (Reader reader = new InputStreamReader(file.getInputStream(),StandardCharsets.UTF_8)) {

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(separator)
                    // .withIgnoreQuotations(false) // Asegúrate que maneje comillas si es necesario (false es default)
                    .build();

            try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(parser)
                    // NO usamos withSkipLines(1) aquí porque necesitamos leer la cabecera nosotros mismos
                    .build()) {


            String []lineaCabeceras = csvReader.readNext();
            if (lineaCabeceras == null || lineaCabeceras.length==0) {
                LOGGER.warn("El archivo esta vacío o no tiene linea de cabecera");
                return macAddressLogs;
            }

            for (int i = 0; i < lineaCabeceras.length; i++) {
                String cabeceraActual = lineaCabeceras[i].trim();
                String cabeceraNormalizada = cabeceraActual.toLowerCase();
                if (cabecerasRequeridas.stream().anyMatch(req -> req.equalsIgnoreCase(cabeceraActual))) {
                    cabecerasMap.put(cabeceraNormalizada, i);
                }
            }

            if (cabecerasMap.size() != cabecerasRequeridas.size()) {
                LOGGER.warn("No se encontraron todas las cabeceras requeridas.");
            }

            String[] datosTotales;
            int numeroLinea = 1;

            while ((datosTotales = csvReader.readNext()) != null) {
                numeroLinea++;

                if (datosTotales.length > 0) {
                    String empresa = obtenerValor(datosTotales, cabecerasMap, headerEmpresaName);
                    String departamento = obtenerValor(datosTotales, cabecerasMap, headerDepartamentoName);
                    String macAddress = obtenerValor(datosTotales, cabecerasMap, headerMacName);
                    String timestampStr = obtenerValor(datosTotales, cabecerasMap, headerTimestampName);

                    LocalDateTime parsedTimestamp = null;
                    if (timestampStr != null) {
                        boolean parsed = false;
                        for (DateTimeFormatter format : formatters) {
                            try {
                                parsedTimestamp = LocalDateTime.parse(timestampStr, format);
                                parsed = true;
                                break;
                            } catch (DateTimeParseException e) {
                            }
                        }
                        if (!parsed) {
                            LOGGER.warn("Línea {}: Valor de timestamp no encontrado o vacío.", numeroLinea);
                        }
                    }
                    if (macAddress != null) {
                        MacAddressLog log = MacAddressLog.builder()
                                .empresa(empresa)
                                .departamento(departamento)
                                .macAddress(macAddress)
                                .timestamp(parsedTimestamp).build();
                        macAddressLogs.add(log);
                    } else {
                        LOGGER.warn("Línea {}: Se omitió el registro porque falta la MacAddress.", numeroLinea);
                    }
                }
            }

            }

        } catch (IOException e) {
            LOGGER.error("Error de I/O al leer el archivo CSV: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el archivo CSV",e);
        }catch (CsvValidationException e) { // Errores específicos de OpenCSV
            LOGGER.error("Error de validación CSV: {}", e.getMessage(), e);
            throw new RuntimeException("Error de formato en el archivo CSV", e);
        }catch (Exception e) {
            // Captura para cualquier otro error inesperado durante el proceso
            LOGGER.error("Error inesperado procesando el archivo CSV: {}", e.getMessage(), e);
            throw new RuntimeException("Error inesperado al procesar el archivo CSV", e);
        }
        return macAddressLogs;
    }

    private String obtenerValor(String[] datos, Map<String, Integer> indices, String nombreCabecera) {
        Integer indice = indices.get(nombreCabecera.toLowerCase());
        if (indice != null && indice < datos.length) {
            return datos[indice].trim();
        }
        return null; // Retorna null si la cabecera no se encontró o el índice está fuera de rango para esa fila
    }
}
