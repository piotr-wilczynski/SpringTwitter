/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wilczynskipio.springtwitter.controller;

import java.util.*;
import org.springframework.social.oauth2.*;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Piotr Wilczynski
 */
@Controller
@RequestMapping("/")
public class TwitterController {

    @Value("${spring.social.twitter.appId}")
    private String appId;
    @Value("${spring.social.twitter.appSecret}")
    private String appSecret;

    @RequestMapping(value = "/*")
    public String index(Model model) {
        model.addAttribute("name", "Piotr");
        return "redirect:/twitter/show";
    }

    @RequestMapping(value = "/twitter/show")
    public ModelAndView show(
            @RequestParam(value = "channel", required = false, defaultValue = "") String channel,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {

        List<Tweet> list;
        if (channel.length() > 0) {
            list = getUserTwitter(channel, fetchApplicationAccessToken());

        } else {
            list = new ArrayList<>();

        }
        if (keyword.length() > 0) {
            //remove all tweets without keyword
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getText().toLowerCase().contains(keyword.toLowerCase())) {
                    list.remove(i);
                    i--;
                }
            }
        }

        ModelAndView model = new ModelAndView("tweets");
        model.addObject("tweets", list);
        model.addObject("channel", channel);
        model.addObject("keyword", keyword);
        return model;
    }

    private static List<Tweet> getUserTwitter(String userName, String appToken) {
        Twitter twitter = new TwitterTemplate(appToken);
        List<org.springframework.social.twitter.api.Tweet> list = twitter.timelineOperations().getUserTimeline(userName, 200);

        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    private String fetchApplicationAccessToken() {
        return fetchApplicationAccessToken(appId, appSecret);
    }

    private String fetchApplicationAccessToken(String appId, String appSecret) {
        // Twitter supports OAuth2 *only* for obtaining an application token, not for user tokens.
        OAuth2Operations oauth = new OAuth2Template(appId, appSecret, "", "https://api.twitter.com/oauth2/token");
        return oauth.authenticateClient().getAccessToken();
    }

}
