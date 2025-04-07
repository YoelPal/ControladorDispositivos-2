package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "switch")
@Getter
@Setter
@NoArgsConstructor
public class Switch  extends Dispositivo{

    @Column(name = "puertos")
    private int puertos;

    @Column(name = "velocidad")
    private int velocidad;

    public Switch(String macAdress,String fabricante,String modelo, String propietario, int puertos, int velocidad){
        super(macAdress,fabricante,modelo, propietario);
        this.puertos = puertos;
        this.velocidad = velocidad;
    }
}
