package practica.ControladorDispositivos.services.MacsManager.Parsers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxtParserService {


    public List<MacAddressLog> txtParser(MultipartFile file) {
        List<MacAddressLog> macAddressLogs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            while((linea = reader.readLine())!= null){

                String[] valores = linea.split(",");
                String sede = valores[0];
                String ip = valores[1];
                String mac = valores[2];

                MacAddressLog macAddressLog = MacAddressLog.builder()
                        .macAddress(mac)
                        .sede(sede)
                        .ip(ip)
                        .build();
                macAddressLogs.add(macAddressLog);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return  macAddressLogs;
    }


}
