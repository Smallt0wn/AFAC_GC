package AFAC.GC.dto;


import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ClubLogoImgDto {

    private String imgName;  // 이미지 파일명
    private String oriImgName;  // 원본 이미지 파일명
    private String imgUrl;      // 이미지 조회 경로
    private String useYn;       // 사용 여부



    private static ModelMapper modelMapper = new ModelMapper();

    public static ClubLogoImgDto of(ClubLogoImgDto clubLogoImgDto) {
        return modelMapper.map(clubLogoImgDto, ClubLogoImgDto.class);
    }

}
