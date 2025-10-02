package practica.ControladorDispositivos.auth.usuario;

import jakarta.persistence.*;
import lombok.*;
import practica.ControladorDispositivos.auth.repository.Token;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "tokens")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String nombre;

    private String password;

    private String rol;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;
}
