package AFAC.GC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String home(){
        return "MainPage";
    }

    @GetMapping("/SettingPage")
    public String setting(){return "SettingPage"; }

    @GetMapping("/member/LoginMainPage")
    public String login(){return "member/LoginMainPage"; }

    @GetMapping("/member/SignUpPage")
    public String signUp(){return "member/SignUpPage"; }

    @GetMapping("/ClubPage")
    public String club(){return "ClubPage"; }

    @GetMapping("/ContentPage")
    public String content(){return "ContentPage"; }

}


