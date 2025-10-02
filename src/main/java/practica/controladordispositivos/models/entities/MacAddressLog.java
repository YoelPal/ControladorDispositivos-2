package practica.controladordispositivos.models.entities;

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
@Table(name = "logs", indexes = {
        @Index(name = "idx_mac_address", columnList = "mc_address"),
        @Index(name = "idx_timestamp", columnList = "timestamp"),
        @Index(name = "idx_sede", columnList = "sede"),
        @Index(name = "idx_ip_switch", columnList = "ip_switch"),
        @Index(name = "idx_vlan", columnList = "vlan"),
        @Index(name = "idx_puerto", columnList = "puerto"),
        @Index(name = "idx_mac_sede", columnList = "mc_address, sede"),
        @Index(name = "idx_ip_switch_puerto", columnList = "ip_switch, puerto")
        // ... otros índices compuestos según tus patrones de consulta
})

public class MacAddressLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
