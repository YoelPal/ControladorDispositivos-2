package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ap")
public class Ap extends Dispositivo{

    @Column(name = "banda")
    private String banda;

    @Column(name = "estandarWifi")
    private int estandarWifi;

    public Ap(){super();}

    public Ap(String macAdress,String fabricante,String modelo, String propietario, String banda, int estandarWifi){
        super(macAdress,fabricante,modelo, propietario);
        this.banda = banda;
        this.estandarWifi = estandarWifi;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public int getEstandarWifi() {
        return estandarWifi;
    }

    public void setEstandarWifi(int estandarWifi) {
        this.estandarWifi = estandarWifi;
    }
}
