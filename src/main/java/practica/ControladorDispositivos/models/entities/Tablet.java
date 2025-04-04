package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tablet")
public class Tablet extends Dispositivo{

    @Column(name = "tamanyo")
    private double tamanyo;

    public Tablet(){
        super();
    }
    public Tablet(String macAdress,String fabricante,String modelo, String propietario, double tamanyo){
        super(macAdress,fabricante,modelo, propietario);
        this.tamanyo = tamanyo;
    }

    public double getTamanyo() {
        return tamanyo;
    }

    public void setTamanyo(double tamanyo) {
        this.tamanyo = tamanyo;
    }
}
