package practica.ControladorDispositivos.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DispositivoDTO {
    private String macAddress;
    private String fabricante;
    private String modelo;
    private String propietario;
    private String sede;
}
