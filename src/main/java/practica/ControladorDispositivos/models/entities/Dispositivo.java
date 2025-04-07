package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

}
