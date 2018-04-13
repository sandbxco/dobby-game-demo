package demo.game;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Component
public class Env {
    @Value("${dobby.url:}")
    String dobbyUrl;

    @Value("${dobby.client_id:}")
    String dobbyClientId;

    @Value("${dobby.client_secret:}")
    String dobbyClientSecret;

    @Value("${dobby.game_uuid:}")
    String dobbyGameUuid;

    @Value("${server.port:}")
    String serverPot;

    public String getAuthorizeUrl() {
        return dobbyUrl.trim() + "/security/oauth/authorize?" +
                "response_type=code&redirect_uri=" + getRedirectUri() +
                "&client_id=" + dobbyClientId;
    }

    public String getTokenUrl(String code) {
        return dobbyUrl.trim() + "/security/oauth/token?" +
                "grant_type=authorization_code" +
                "&redirect_uri=" + getRedirectUri() +
                "&code=" + code;
    }

    public String getClientCredentialsBase64() {
        return Base64.getEncoder().encodeToString((dobbyClientId + ":" + dobbyClientSecret).getBytes());
    }

    public String getRedirectUri() {
        return "http://localhost:" + serverPot + "/authorize";
    }

    public String getDobbyUrl() {
        return dobbyUrl;
    }

    public String getDobbyGameUuid() {
        return dobbyGameUuid;
    }
}