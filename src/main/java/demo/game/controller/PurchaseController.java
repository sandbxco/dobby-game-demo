package demo.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.game.Env;
import demo.game.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PurchaseController {
    @Autowired
    private Env env;

    @GetMapping("/purchases")
    public ModelAndView consume1(@RequestParam(required = false) String consumeUuid, HttpSession session) {
        String accessToken = (String) session.getAttribute("access_token");
        String username = (String) session.getAttribute("username");
        if (accessToken == null) {
            return new ModelAndView("redirect:/login.html");
        }

        ModelAndView model = new ModelAndView("purchases");

        if (consumeUuid != null) {
            String status = consumePurchase(consumeUuid, accessToken);
            model.addObject("status", "Purchase " + consumeUuid + " consume status: " + status);
        }

        PurchaseResponse purchaseResponse = getPurchases(accessToken);
        model.addObject("username", username);
        model.addObject("purchases", purchaseResponse.purchases);
        model.addObject("dobby_url", env.getDobbyUrl());

        return model;
    }


    private PurchaseResponse getPurchases(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = env.getDobbyUrl() + "/api/portal/api/games/" + env.getDobbyGameUuid() + "/purchases/";
        ResponseEntity<PurchaseResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PurchaseResponse.class);


        return response.getBody();
    }


    private String consumePurchase(String purchaseUuid, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        String url = env.getDobbyUrl() + "/api/portal/api/purchases/" + purchaseUuid + "/consume";
        ResponseEntity<String> responseJson = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Map<String, Object> responseMap = parseJson(responseJson.getBody());
        return responseMap.get("status").toString();
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