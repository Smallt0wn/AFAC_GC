package AFAC.GC.controller;

import AFAC.GC.dto.MemberFormDto;
import AFAC.GC.entity.Member;
import AFAC.GC.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/new")
    public String memberForm(Model model) {
        model.addAttribute("MemberFormDto", new MemberFormDto());
        return "member/SignUpPage";
    }

    @PostMapping("/member/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField() + "Error", error.getDefaultMessage());
            }
            model.addAttribute("MemberFormDto", memberFormDto); // 유지하고자 하는 입력 데이터를 다시 추가
            model.addAllAttributes(errorMap); // 모든 에러 메시지를 모델에 추가
            return "member/SignUpPage"; // 에러와 함께 회원가입 페이지로 리턴
        }
            // 이후, 중복검사 진행
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/SignUpPage";
        }
        return "redirect:/"; // 회원가입 성공 시 홈으로 리디렉션
    }

    @GetMapping(value = "/member/login")
    public String loginMember(){
        return "/member/LoginMainPage";
    }

    @GetMapping(value = "/member/login/error")
    public String errorLoginMember(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");

        return "/member/LoginMainPage";
    }

}
