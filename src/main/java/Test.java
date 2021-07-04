import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String secret = "charlie123";
        String issuer = "god";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = "";

        // create token
        try {
            Date expireAfterOneDay = DateUtil.addDays(new Date(), 1);

            token = JWT.create()
                    .withIssuer(issuer)
                    .withExpiresAt(expireAfterOneDay)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            System.out.println("Exception :" + exception.getMessage());
        }

        System.out.println("Create Token = " + token);

        // verify token and validity

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            Date tokenExpireTime =  jwt.getExpiresAt();

            Date decodeTime = new Date();

            if(tokenExpireTime.after(decodeTime)) {
                System.out.println("Token is Valid");
            } else {
                System.out.println("Token has Expired");
            }
        } catch (JWTVerificationException exception){
            System.out.println("Exception :" + exception.getMessage());
        }
    }
}
