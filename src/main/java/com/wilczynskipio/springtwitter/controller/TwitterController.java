/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wilczynskipio.springtwitter.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Piotr Wilczynski
 */
@Controller
@RequestMapping("/")
public class TwitterController {

    private String appToken;

    public TwitterController() {
        appToken = fetchApplicationAccessToken();
    }

    @RequestMapping(value = "/twitter")
    public String index(Model model) {
        model.addAttribute("name", "Piotr");
        return "twitter";
    }

    @RequestMapping(value = "/twitter/show")
    public String index(@RequestParam(value = "channel", required = false, defaultValue = "#springframework") String channel, Model model) {
        model.addAttribute("tweets", getUserTwitter(channel, appToken));
        return "tweets";
    }

    private static List<Tweet> searchTwitter(String query, String appToken) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        // Using application token for search so that we don't have to go through hassle of getting a user token.
        // This is not (yet) supported by Spring Social, so we must construct the request ourselves.
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + appToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        Map<String, ?> result = rest.exchange("https://api.twitter.com/1.1/search/tweets.json?q={query}", HttpMethod.GET, requestEntity, Map.class, query).getBody();
        List<Map<String, ?>> statuses = (List<Map<String, ?>>) result.get("statuses");
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (Map<String, ?> status : statuses) {
            Iterator<String> iterator = status.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println(key + " " + status.get(key));
            }
            tweets.add(new Tweet(Long.valueOf(status.get("id").toString()), status.get("text").toString()));
            System.out.println("");
        }
        return tweets;
    }
    
    private static List<Tweet> getUserTwitter(String userName,String appToken) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + appToken);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set("include_entities", "true");
        parameters.set("screen_name", userName);
        //parameters.set("count", "");
        URI uri = URIBuilder.fromUri(API_URL_BASE + "statuses/user_timeline.json").queryParams(parameters).build();        
        List<Map<String, ?>> result = rest.exchange(uri, HttpMethod.GET, requestEntity, List.class).getBody();
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (Map<String, ?> status : result) {
            tweets.add(new Tweet(Long.valueOf(status.get("id").toString()), status.get("text").toString()));
        }
        return tweets;
        
    }

    private static String fetchApplicationAccessToken() {
        return fetchApplicationAccessToken("RNFlQKDIyE4xco7bSxPkGlboL", "MyH6VJLqRWoOOEDsHSQpPeOt4jt0cXKnU3lT6JSR81ddyXaaSA");
    }

    private static String fetchApplicationAccessToken(String appId, String appSecret) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        OAuth2Operations oauth = new OAuth2Template(appId, appSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth.authenticateClient().getAccessToken();
    }

    private static final class Tweet {

        private long id;

        private String text;

        public Tweet(long id, String text) {
            this.id = id;
            this.text = text;
        }

        public long getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }

    private static final String API_URL_BASE = "https://api.twitter.com/1.1/";
    
    @SuppressWarnings("serial")
    private static class TweetList extends ArrayList<org.springframework.social.twitter.api.Tweet> {
    }

}
