package practica.controladordispositivos.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IpDTO {
    private Long id;
    private String ipAddress;
    private String macAddress;
}
