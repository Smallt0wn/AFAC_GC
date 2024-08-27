package AFAC.GC.entity;


import AFAC.GC.constant.Role;
import AFAC.GC.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String phoneNumber;
    private String password;

    private String studentNumber;

    private String department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Club> clubs = new ArrayList<>();


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPhoneNumber(memberFormDto.getPhoneNumber());

        member.setStudentNumber(memberFormDto.getStudentNumber());
        member.setDepartment(memberFormDto.getDepartment());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);

        member.setRole(Role.ADMIN);

        return member;
    }
}
