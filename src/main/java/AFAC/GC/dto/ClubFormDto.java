package AFAC.GC.dto;

import AFAC.GC.constant.ClassifyClub;
import AFAC.GC.entity.Club;
import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.entity.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ClubFormDto {

    private Long id;

    @NotBlank(message = "동아리 이름은 필수 입력 값입니다.")
    private String clubName;

    private ClassifyClub classifyClub; // "중앙동아리", "학과동아리"
    // => 스크롤 방식으로 하나를 고르면 넘어가는 방식 택할 예정

    @NotBlank(message = "동아리 소개는 필수 입력 값입니다.")
    private String description;

    @NotBlank(message = "SNS LINK는 필수 입력 값입니다.")
    private String snsLink;

    @NotBlank(message = "대표 연락처는 필수 입력 값입니다.")
    private String contactNumber;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private Member member;


    private List<ClubLogoImgDto> clubLogoImgDtoList = new ArrayList<>();
    private List<Long> clubLogoImgIdList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper(); // 사용하지 못했다 ㅠ
    public Club createClub(){
        return modelMapper.map(this, Club.class);
    }

    public static ClubFormDto of(Club club) {
        return modelMapper.map(club, ClubFormDto.class);
    }

}
