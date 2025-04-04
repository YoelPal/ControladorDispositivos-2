package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "router")
public class Router extends Dispositivo{
    @Column(name = "velocidad")
    private int velocidad;

    @Column(name = "ancho_banda")
    private int anchoBanda;

    public Router(){super() ;}

    public Router(String macAdress,String fabricante,String modelo, String propietario,int velocidad, int anchoBanda){
        super(macAdress,fabricante,modelo, propietario);
        this.anchoBanda = anchoBanda;
        this.velocidad = velocidad;

    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getAnchoBanda() {
        return anchoBanda;
    }

    public void setAnchoBanda(int anchoBanda) {
        this.anchoBanda = anchoBanda;
    }
}
