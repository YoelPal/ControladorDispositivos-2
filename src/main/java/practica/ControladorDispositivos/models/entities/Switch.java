package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "switch")
public class Switch  extends Dispositivo{

    @Column(name = "puertos")
    private int puertos;

    @Column(name = "velocidad")
    private int velocidad;

    public Switch(){super();}

    public Switch(String macAdress,String fabricante,String modelo, String propietario, int puertos, int velocidad){
        super(macAdress,fabricante,modelo, propietario);
        this.puertos = puertos;
        this.velocidad = velocidad;
    }

    public int getPuertos() {
        return puertos;
    }

    public void setPuertos(int puertos) {
        this.puertos = puertos;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
