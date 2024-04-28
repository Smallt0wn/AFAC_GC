package AFAC.GC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String home(){
        return "MainPage";
    }

    @GetMapping("/LoginMainPage")
    public String login(){return "LoginMainPage"; }

    @GetMapping("/SettingPage")
    public String setting(){return "SettingPage"; }

    @GetMapping("/SignUpPage")
    public String signup(){return "SignUpPage"; }

    @GetMapping("/ClubPage")
    public String club(){return "ClubPage"; }

    @GetMapping("/ContentPage")
    public String content(){return "ContentPage"; }
}


