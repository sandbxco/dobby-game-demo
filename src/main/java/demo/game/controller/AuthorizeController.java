package demo.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.game.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class AuthorizeController {
    @Autowired
    private Env env;

    @GetMapping("/authorize")
    public String purchases(@RequestParam(required = false) String code, HttpSession session) {
        if (code != null) {
            String accessToken = getAccessToken(code);

            String username = getUsername(accessToken);
            session.setAttribute("access_token", accessToken);
            session.setAttribute("username", username);
            return "redirect:purchases";
        } else {
            return "redirect:error";
        }
    }

    private String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + env.getClientCredentialsBase64());

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = env.getTokenUrl(code);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String authJson = response.getBody();

        Map<String, Object> auth = parseJson(authJson);

        return auth.get("access_token").toString();
    }


    private String getUsername(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = env.getDobbyUrl() + "/api/portal/api/user";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String userJson = response.getBody();

        Map<String, Object> user = parseJson(userJson);

        return user.get("loginEmail").toString();
    }


    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse JSON: " + json);
        }
    }

}