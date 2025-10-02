package practica.controladordispositivos.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import practica.controladordispositivos.auth.usuario.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    public String generateToken(final User user){
        return buildToken(user,jwtExpiration);
    }

    public String generateRefreshToken(final User user){
        return buildToken(user,refreshExpiration);
    }

    private String buildToken(final User user, final long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of(
                        "mail", user.getEmail(),
                        "rol", user.getRol(),
                        "userId",user.getId()))
                .subject(user.getNombre())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public Long extractUserId(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.get("userId", Long.class);
    }

    public boolean isTokenValid(final String token, final User user){
        final String username = extractUsername(token);
        return (username.equals(user.getNombre()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(final String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }
}
