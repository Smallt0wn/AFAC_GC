package AFAC.GC.service;

import AFAC.GC.dto.MemberFormDto;
import AFAC.GC.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Member createUser(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("test1@gachon.ac.kr");
        memberFormDto.setName("홍길동");
        memberFormDto.setPassword("123456");
        memberFormDto.setDepartment("소프트웨어학과");
        memberFormDto.setPhoneNumber("01012345678");
        memberFormDto.setStudentNumber("123456");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest(){
        Member member = createUser();

        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPhoneNumber(), savedMember.getPhoneNumber());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getDepartment(), savedMember.getDepartment());
        assertEquals(member.getStudentNumber(), savedMember.getStudentNumber());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createUser();
        Member member2 = createUser();
        memberService.saveMember(member1);
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);});
        assertEquals("already exist User", e.getMessage());
    }

}