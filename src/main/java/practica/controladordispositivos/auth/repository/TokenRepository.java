package practica.controladordispositivos.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import practica.controladordispositivos.auth.usuario.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query(value = "select t from tokens t inner join User u on t.user.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
    Optional<Token> findByTokenAndTokenType(String token, Token.TokenType tokenType);
    Optional<Token> findByTokenAndTokenTypeAndUser(String token, Token.TokenType tokenType, User user);
}
