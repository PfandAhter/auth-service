package com.hotelreservation.authservice.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.hotelreservation.authservice.constants.Constants.JWT_CRTPYO_SECRET;
import static com.hotelreservation.authservice.constants.Constants.JWT_SECRET;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private static final int EXPIREDATE = 1000 * 60 * 5/**Minute**/
            ;


    private final Environment environment;
    @Value(JWT_SECRET)
    private String jwtSecret;

    @Value(JWT_CRTPYO_SECRET)
    private String jwtCrtpyoSecret;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String jwt = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIREDATE)))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

        try {
            return EncryptionUtil.encrypt(jwt, jwtCrtpyoSecret);
        } catch (Exception e) {
            log.error("generateToken error: ", e);
        }
        return null;
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        // we want to make sure that username we have within the token is the same as the username we have as input(userdetails)

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
        // .before - > because it's a date , i want to make sure it's before today's date
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

        //expiration = sure sonu , sona ermesi.
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String decryptJwt(String token) {
        try {
            return EncryptionUtil.decrypt(token, jwtCrtpyoSecret);
        } catch (Exception e) {
            log.error("decryptJwt error: ", e);
        }
        return null;
    }

}
