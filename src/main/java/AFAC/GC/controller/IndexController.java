package AFAC.GC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/SettingPage")
    public String setting(){return "SettingPage"; }

    @GetMapping("/")
    public String MainPage() {
        return "/MainPage";
    }

    @GetMapping("/member/LoginMainPage")
    public String login(){return "member/LoginMainPage"; }

    @GetMapping("/member/SignUpPage")
    public String signUp(){return "member/SignUpPage"; }

    @GetMapping("/ContentPage")
    public String content(){return "ContentPage"; }

}


