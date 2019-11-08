package ir.ac.kntu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    public static final long JWT_TOKEN_VALIDITY = 6 * 60 * 60 * 1000;//milli seconds

    @Value("${jwt.secret}")
    private String secret;

    public String token2username(String token){
        return token2claim(token, Claims::getSubject);
    }

    public Date token2expirationDate(String token){
        return token2claim(token, Claims::getExpiration);
    }

    private <T> T token2claim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = token2username(token);
        return ((!isExpiredToken(token)) && userDetails.getUsername().equals(username));
    }

    private boolean isExpiredToken(String token){
        final Date expirationDate = token2expirationDate(token);
        return expirationDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        String username = userDetails.getUsername();
        return Jwts.builder()
                .setClaims(new HashMap<String, Object>())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
