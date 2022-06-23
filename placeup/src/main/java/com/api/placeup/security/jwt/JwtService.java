package com.api.placeup.security.jwt;

import com.api.placeup.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken( User user ){
        long expString = Long.valueOf(expiration);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(expString);
        Instant instant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getLogin())
                .setExpiration(data)
                .signWith( SignatureAlgorithm.HS512, signatureKey )
                .compact();
    }

    private Claims getClaims( String token ) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValidation( String token ){
        try{
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime date =
                    expirationDate.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        }catch (Exception e){
            return false;
        }
    }

    public String getUserLogin(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
