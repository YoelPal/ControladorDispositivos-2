package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "tablet")
@Getter
@Setter
@NoArgsConstructor
public class Tablet extends Dispositivo{

    @Column(name = "tamanyo")
    private double tamanyo;

    public Tablet(String macAdress, String fabricante, String modelo, String propietario, double tamanyo, String sede, List<Ip> ips){
        super(macAdress,fabricante,modelo, propietario,sede,ips);
        this.tamanyo = tamanyo;
    }
}
