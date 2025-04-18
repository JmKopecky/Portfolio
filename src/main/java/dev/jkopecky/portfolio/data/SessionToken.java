package dev.jkopecky.portfolio.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class SessionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;


    public static SessionToken createToken(SessionTokenRepository repository) {
        SessionToken sToken = new SessionToken();
        sToken.token = UUID.randomUUID().toString();
        repository.save(sToken);
        return sToken;
    }


    public String getToken() {
        return token;
    }
}
