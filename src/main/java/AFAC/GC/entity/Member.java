package AFAC.GC.entity;

import AFAC.GC.constant.Role;
import AFAC.GC.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String studentNumber;

    @Column(nullable = false)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Club> clubs = new ArrayList<>();

    // Builder 적용
    @Builder
    private Member(String email, String name, String phoneNumber, String password,
                   String studentNumber, String department, Role role) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.studentNumber = studentNumber;
        this.department = department;
        this.role = role;
    }

    // 정적 팩토리 메서드 (MemberFormDto와 PasswordEncoder 사용)
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(memberFormDto.getEmail())
                .name(memberFormDto.getName())
                .phoneNumber(memberFormDto.getPhoneNumber())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .studentNumber(memberFormDto.getStudentNumber())
                .department(memberFormDto.getDepartment())
                .role(Role.ADMIN) // 기본 역할을 ADMIN으로 설정
                .build();
    }

    // 비즈니스 메서드: Role 변경
    public void changeRole(Role newRole) {
        this.role = newRole;
    }

    // 비즈니스 메서드: 클럽 추가
    public void addClub(Club club) {
        this.clubs.add(club);
        club.assignMember(this); // 양방향 연관관계 설정
    }

    // 비즈니스 메서드: 클럽 제거
    public void removeClub(Club club) {
        this.clubs.remove(club);
        club.removeMember(); // 양방향 연관관계 해제
    }
}
