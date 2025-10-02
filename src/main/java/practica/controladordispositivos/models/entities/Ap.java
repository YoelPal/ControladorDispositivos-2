package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ap")
@Getter
@Setter
@NoArgsConstructor
public class Ap extends Dispositivo{

    @Column(name = "banda")
    private String banda;

    @Column(name = "estandarWifi")
    private int estandarWifi;


    public Ap(String macAdress, String fabricante, String modelo, String propietario, String banda, int estandarWifi, String sede, List<Ip> ips){
        super(macAdress,fabricante,modelo, propietario, sede, ips );
        this.banda = banda;
        this.estandarWifi = estandarWifi;

    }

}
