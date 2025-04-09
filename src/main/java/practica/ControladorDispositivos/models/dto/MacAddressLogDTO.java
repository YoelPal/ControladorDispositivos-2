package practica.ControladorDispositivos.models.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MacAddressLogDTO {
    private String mcAdress;
    private LocalDateTime timestamp;
    private String departamento;
    private String Empresa;

}
