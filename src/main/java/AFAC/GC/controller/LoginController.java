package AFAC.GC.controller;


import AFAC.GC.dto.MemberFormDto;
import AFAC.GC.entity.Member;
import AFAC.GC.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor

public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/new")
    public String userForm(Model model) {
        model.addAttribute("MemberFormDto", new MemberFormDto());
        return "member/SignUpPage";
    }

    @PostMapping("/new")
    public String newUser(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/SignUpPage";
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/SignUpPage";
        }
        return "redirect:/";
    }

}
