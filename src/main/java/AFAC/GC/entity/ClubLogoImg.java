package AFAC.GC.entity;

import AFAC.GC.constant.UseStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ClubLogoImg extends BaseEntity {

    @Id
    @Column(name = "club_logo_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;  // 이미지 파일명
    private String oriImgName;  // 원본 이미지 파일명
    private String imgUrl;      // 이미지 조회 경로

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UseStatus useYn; // 사용 여부를 Enum으로 관리

    @OneToOne(mappedBy = "clubLogoImg", fetch = FetchType.LAZY)
    private Club club;

    // 생성자 (Builder를 통해 사용)
    @Builder
    public ClubLogoImg(String imgName, String oriImgName, String imgUrl, UseStatus useYn, Club club) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.useYn = useYn;
        this.club = club;
    }

    // 비즈니스 메서드로 데이터 변경
    public void updateClubImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
