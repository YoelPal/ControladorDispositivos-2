package practica.ControladorDispositivos.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "router")
@Getter
@Setter
@NoArgsConstructor
public class Router extends Dispositivo{
    @Column(name = "velocidad")
    private int velocidad;

    @Column(name = "ancho_banda")
    private int anchoBanda;

    public Router(String macAdress,String fabricante,String modelo, String propietario,int velocidad, int anchoBanda){
        super(macAdress,fabricante,modelo, propietario);
        this.anchoBanda = anchoBanda;
        this.velocidad = velocidad;

    }
}
