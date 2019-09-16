package cc.domovoi.tools.certification;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;

public class JwtUtils {

    public static ZoneOffset zoneOffset = ZoneOffset.of("+8");

    public static String secret = "UzR5NVVBd1F1QkZGN2VFZw==";

    public static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public static Long expiration = 604800L;

    public static void zoneOffset(String offsetId) {
        zoneOffset = ZoneOffset.of(offsetId);
    }

    public static void secret(String newSecret) {
        secret = Base64.getEncoder().encodeToString(newSecret.getBytes());
    }

    public static void expiration(Long newExpiration) {
        expiration = newExpiration;
    }

    public static String createToken(Map<String, Object> claims, String base64EncodedSecretKey, Optional<LocalDateTime> expirationTime, Optional<LocalDateTime> notBeforeTime, Optional<SignatureAlgorithm> signatureAlgorithm, Consumer<? super JwtBuilder> op) {
        JwtBuilder builder = Jwts.builder().addClaims(claims);
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));
        if (signatureAlgorithm.isPresent()) {
            builder.signWith(key, signatureAlgorithm.get());
        }
        else {
            builder.signWith(key);
        }
        expirationTime.ifPresent(time -> builder.setExpiration(Date.from(time.toInstant(zoneOffset))));
        notBeforeTime.ifPresent(time -> builder.setNotBefore(Date.from(time.toInstant(zoneOffset))));
        op.accept(builder);
        return builder.compact();
    }

    public static String createToken(Map<String, Object> claims, String base64EncodedSecretKey, LocalDateTime expirationTime, LocalDateTime notBeforeTime, Consumer<? super JwtBuilder> op) {
        return createToken(claims, base64EncodedSecretKey, Optional.ofNullable(expirationTime), Optional.ofNullable(notBeforeTime), Optional.empty(), op);
    }

    public static String createToken(Map<String, Object> claims, String base64EncodedSecretKey, LocalDateTime expirationTime, LocalDateTime notBeforeTime) {
        return createToken(claims, base64EncodedSecretKey, Optional.ofNullable(expirationTime), Optional.ofNullable(notBeforeTime), Optional.empty(), jwtBuilder -> {});
    }

    public static String createToken(Map<String, Object> claims, String base64EncodedSecretKey, LocalDateTime expirationTime) {
        return createToken(claims, base64EncodedSecretKey, Optional.ofNullable(expirationTime), Optional.empty(), Optional.empty(), jwtBuilder -> {});
    }

    public static Optional<Claims> parseToken(String token, String base64EncodedSecretKey) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody());
        } catch (JwtException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Boolean isTokenValid(Claims claims) {
        Optional<LocalDateTime> expirationTime = Optional.ofNullable(claims.getExpiration()).map(date -> LocalDateTime.ofInstant(date.toInstant(), zoneOffset));
        Optional<LocalDateTime> notBeforeTime = Optional.ofNullable(claims.getNotBefore()).map(date -> LocalDateTime.ofInstant(date.toInstant(), zoneOffset));
        LocalDateTime now = LocalDateTime.now();
        return expirationTime.map(now::isBefore).orElse(true) && notBeforeTime.map(now::isAfter).orElse(true);
    }

    public static Boolean isTokenExpired(Claims claims) {
        return !isTokenValid(claims);
    }

    public static Optional<String> refreshToken(String token, String base64EncodedSecretKey, LocalDateTime expirationTime) {
        return parseToken(token, base64EncodedSecretKey).map(claims -> createToken(claims, base64EncodedSecretKey, expirationTime));
    }

    public static Optional<String> disableToken(String token, String base64EncodedSecretKey) {
        return parseToken(token, base64EncodedSecretKey).map(claims -> createToken(claims, base64EncodedSecretKey, LocalDateTime.now()));
    }


}
