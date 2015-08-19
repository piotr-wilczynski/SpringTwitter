/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wilczynskipio.springtwitter.controller;

import java.util.List;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(@RequestParam(value = "channel", required = false, defaultValue = "springframework") String channel, Model model) {
        model.addAttribute("tweets", getUserTwitter(channel, appToken));
        model.addAttribute("channels", new String[]{"diana_falkowska","wilczynskipio"});
        return "tweets";
    }

    private static List<Tweet> searchTwitter(String query, String appToken) {
        Twitter twitter = new TwitterTemplate(appToken);
        SearchResults results = twitter.searchOperations().search(query);
        return results.getTweets();
    }
    
    
    private static List<org.springframework.social.twitter.api.Tweet> getUserTwitter(String userName,String appToken) {
        Twitter twitter = new TwitterTemplate(appToken);
        List<org.springframework.social.twitter.api.Tweet> list = twitter.timelineOperations().getUserTimeline(userName);
        
        return list;
    }

    private static String fetchApplicationAccessToken() {
        return fetchApplicationAccessToken("RNFlQKDIyE4xco7bSxPkGlboL", "MyH6VJLqRWoOOEDsHSQpPeOt4jt0cXKnU3lT6JSR81ddyXaaSA");
    }

    private static String fetchApplicationAccessToken(String appId, String appSecret) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        OAuth2Operations oauth = new OAuth2Template(appId, appSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth.authenticateClient().getAccessToken();
    }

}
