package practica.ControladorDispositivos.models.dto;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacAddressLogDTO {
    private Long id;
    private String macAddress;
    private LocalDateTime timestamp;
    private String sede;
    private String ipSwitch;
    private String vlan;
    private String puerto;
    private String ip;
    private String hostname;
    private String directorio;

}
