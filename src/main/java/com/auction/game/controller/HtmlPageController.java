package com.auction.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlPageController {

    @GetMapping(path = "/", headers = {"Application-Content: text/html"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(path = "/main", headers = {"Application-Content: text/html"})
    public String getMainPage() {
        return "index";
    }

}
