package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")

public class MacAddressLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mc_address")
    private String macAddress;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "sede")
    private String sede;

    @Column(name = "ip_switch")
    private String ipSwitch;

    @Column(name = "vlan")
    private String vlan;

    @Column(name = "puerto")
    private String puerto;

    @Column(name = "ip")
    private String ip;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "directorio")
    private String directorio;




}
