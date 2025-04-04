package practica.ControladorDispositivos.services.impl;

import org.springframework.stereotype.Service;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.repositories.DispositivoRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class DispositivoScannerService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoScannerService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    // Método para hacer ping a un rango de IPs
    private void scanNetwork(String subnet) {
        for (int i = 1; i < 255; i++) {
            String ip = subnet + i;
            try {
                Process process = Runtime.getRuntime().exec("ping -c 1 " + ip); // En Windows usa "ping -n 1"
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para obtener direcciones MAC
    private Map<String, String> getMacAddresses() {
        Map<String, String> macDevices = new HashMap<>();
        try {
            Process process = Runtime.getRuntime().exec("arp -a");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("-")) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 2) {
                        macDevices.put(parts[0], parts[1]); // IP -> MAC
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macDevices;
    }
}
