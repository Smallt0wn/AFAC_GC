package AFAC.GC.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class ClubLogoImg extends BaseEntity {

    @Id
    @Column(name="club_logo_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;  // 이미지 파일명
    private String oriImgName;  // 원본 이미지 파일명
    private String imgUrl;      // 이미지 조회 경로
    private String useYn;       // 사용 여부

    @OneToOne(mappedBy = "clubLogoImg", fetch = FetchType.LAZY)
    private Club club;

    public void updateClubImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
