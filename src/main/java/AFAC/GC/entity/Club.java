package AFAC.GC.entity;

import AFAC.GC.constant.ClassifyClub;
import AFAC.GC.dto.ClubFormDto;
import AFAC.GC.dto.ClubLogoImgDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club")
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Club extends BaseEntity{

    @Id
    @Column(name="club_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clubName;

    private ClassifyClub classifyClub; // "중앙동아리", "학과동아리" CentralClub, DepartmentClub

    @Lob
    private String description;

    private String snsLink;

    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "club_logo_img_id") // 외래 키로 사용
    private ClubLogoImg clubLogoImg;

    public static Club createClub(ClubFormDto clubFormDto, Member member) {
            Club club = new Club();

            club.setClubName(clubFormDto.getClubName()); // 동아리 이름 설정
            club.setClassifyClub(clubFormDto.getClassifyClub()); // Enum 값 설정

            club.setDescription(clubFormDto.getDescription()); // 동아리 소개 설정
            club.setSnsLink(clubFormDto.getSnsLink()); // SNS 링크 설정
            club.setContactNumber(clubFormDto.getContactNumber()); // 대표 연락처 설정
            club.setMember(member); // 동아리장 설정
        
            return club;
        }
}
