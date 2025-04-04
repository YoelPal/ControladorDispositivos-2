package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movil")
public class Movil extends Dispositivo {

    @Column(name = "tamanyo")
    private double tamanyo;

    public Movil(String macAddress, String fabricante, String modelo,String propietario, double tamanyo ) {
        super(macAddress, fabricante, modelo, propietario);
        this.tamanyo = tamanyo;
    }

    public Movil() {
      super();
    }

    public double getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(double tamanyo) {
        this.tamanyo = tamanyo;
    }
}


