package AFAC.GC.entity;

import AFAC.GC.constant.ClassifyClub;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String clubName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassifyClub classifyClub;

    @Lob
    private String description;

    private String snsLink;
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 연관 관계 설정 메서드
    public void assignMember(Member member) {
        this.member = member; // Member와 연관 설정
    }

    public void removeMember() {
        this.member = null; // Member와의 연관 관계 해제
    }

    @Builder
    public Club(String clubName, ClassifyClub classifyClub, String description, String snsLink, String contactNumber) {
        this.clubName = clubName;
        this.classifyClub = classifyClub;
        this.description = description;
        this.snsLink = snsLink;
        this.contactNumber = contactNumber;
    }
}
