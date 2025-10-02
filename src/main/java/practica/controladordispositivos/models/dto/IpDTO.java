package practica.ControladorDispositivos.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IpDTO {
    private Long id;
    private String ipAddress;
    private String macAddress;
}
