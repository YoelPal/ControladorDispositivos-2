package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movil")
@Getter
@Setter
@NoArgsConstructor
public class Movil extends Dispositivo {

    @Column(name = "tamanyo")
    private double tamanyo;

    public Movil(String macAddress, String fabricante, String modelo,String propietario, double tamanyo ) {
        super(macAddress, fabricante, modelo, propietario);
        this.tamanyo = tamanyo;
    }

}


