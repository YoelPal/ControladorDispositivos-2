package practica.ControladorDispositivos.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import practica.ControladorDispositivos.models.entities.Ip;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispositivoDTO {
    private String macAddress;
    private String fabricante;
    private String modelo;
    private String propietario;
    private String sede;
    private String tipoDispositivo;
    private List<IpDTO> ips;
}
