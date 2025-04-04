package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.*;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "dispositivo")
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

    public Dispositivo(){}


    public Dispositivo(String macAddress, String fabricante, String modelo,String propietario){
        this.fabricante = fabricante;
        this.macAddress = macAddress;
        this.modelo = modelo;
        this.propietario=propietario;
    }


    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

}
