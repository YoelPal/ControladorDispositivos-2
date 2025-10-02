package practica.ControladorDispositivos.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "dispositivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Dispositivo {

    @Id
    @Column(name = "mac_address", nullable = false,unique = true)
    protected String macAddress;

    @Column(name = "fabricante")
    protected String fabricante;

    @Column(name = "modelo")
    protected String modelo;

    @Column(name = "propietario")
    protected String propietario;

    @Column(name = "sede")
    protected String sede;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ip> ips = new ArrayList<>();

    public void addIp(Ip ip){
        ips.add(ip);
        ip.setDispositivo(this);
    }

    public void removeIp(Ip ip) {
        ips.remove(ip);
        ip.setDispositivo(null); // Romper la relación inversa
    }
}
