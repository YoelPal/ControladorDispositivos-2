package practica.ControladorDispositivos.auth.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import practica.ControladorDispositivos.auth.repository.Token;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    @Column(unique = true)
    private String nombre;

    private String password;

    private String rol;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
