package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    public Ap(String macAdress,String fabricante,String modelo, String propietario, String banda, int estandarWifi){
        super(macAdress,fabricante,modelo, propietario);
        this.banda = banda;
        this.estandarWifi = estandarWifi;
    }

}
