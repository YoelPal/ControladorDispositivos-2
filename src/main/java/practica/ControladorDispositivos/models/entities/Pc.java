package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pc")
@Getter
@Setter
@NoArgsConstructor
public class Pc extends Dispositivo{

    @Column(name = "sistema_operativo")
    private String sistemaOperativo;

    @Column(name = "ram")
    private int ram;

    @Column(name = "cpu")
    private String cpu;


    public Pc(String macAdress, String fabricante, String modelo, String propietario,String sede, String sistemaOperativo, int ram, String cpu){
        super(macAdress,fabricante,modelo, propietario, sede);
        this.cpu = cpu;
        this.ram = ram;
        this.sistemaOperativo = sistemaOperativo;
    }

}
