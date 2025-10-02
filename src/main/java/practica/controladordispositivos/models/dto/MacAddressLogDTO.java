package practica.controladordispositivos.models.dto;


import lombok.*;

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
