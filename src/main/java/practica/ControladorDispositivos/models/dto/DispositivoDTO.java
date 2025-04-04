package practica.ControladorDispositivos.models.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispositivoDTO {
    private String macAddress;
    private String sede;
    private String empresa;
    private LocalDateTime timestamp;


}
