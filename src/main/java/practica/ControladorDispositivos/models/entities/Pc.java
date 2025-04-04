package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pc")
public class Pc extends Dispositivo{

    @Column(name = "sistema_operativo")
    private String sistemaOperativo;

    @Column(name = "ram")
    private int ram;

    @Column(name = "cpu")
    private String cpu;

    public Pc(){
        super();
    }

    public Pc(String macAdress, String fabricante, String modelo, String propietario, String sistemaOperativo, int ram, String cpu){
        super(macAdress,fabricante,modelo, propietario);
        this.cpu = cpu;
        this.ram = ram;
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
}
