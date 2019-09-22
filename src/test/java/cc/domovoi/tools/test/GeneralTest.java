package cc.domovoi.tools.test;

import cc.domovoi.tools.certification.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Encoders;
//import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GeneralTest {

    Logger logger = LoggerFactory.getLogger(GeneralTest.class);

    @Test
    public void testJwt() throws Exception {
        String key0 = Base64.getEncoder().encodeToString("Nunb9qA2Cp4gVCGfFDM45LAmPeoZA01L7TeHCFbnlXwCReW9TBcwe7g79OabsP8Zx3BjelvZhA03rkpNkiDterygwjUXRzR65CdsmLEbD3stZAzNF61ibzaWC7uM88Ze".getBytes());
//        logger.debug(key0);
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        logger.debug("key: " + key.getFormat());
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("haha", "hehe");
//        claims.put("hihi", "hoho");
//        String base64EncodedSecretKey = Encoders.BASE64.encode(key.getEncoded());
//        String token = JwtUtils.createToken(claims, base64EncodedSecretKey, LocalDateTime.now().plusDays(7L));
//        logger.debug("token: " + token);
//        Optional<Claims> claimsOptional = JwtUtils.parseToken(token, base64EncodedSecretKey);
//        claimsOptional.ifPresent(claims1 -> {
//            claims1.forEach((k, v) -> logger.debug(String.format("%s -> %s", k, v)));
//        });
    }
}
