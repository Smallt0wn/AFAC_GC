package AFAC.GC.service;

import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.repository.ClubLogoImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubLogoImgService {

    @Value("${ClubLogoImgLocation}")
    private String clubLogoImgLocation;
    private final ClubLogoImgRepository clubLogoImgRepository;

    private final FileService fileService;

    public void saveClubLogoImg(ClubLogoImg clubLogoImg, MultipartFile ClubLogoImgFile) throws Exception{
        String oriImgName = ClubLogoImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(clubLogoImgLocation, oriImgName,
                    ClubLogoImgFile.getBytes());
            imgUrl = "/logo/" + imgName;
        }

        //동아리 로고 이미지 저장
        clubLogoImg.updateClubImg(oriImgName, imgName, imgUrl);
        clubLogoImgRepository.save(clubLogoImg);
    }

//    public long getClubLogo(){
//        return clubLogoImgRepository.findByClubIdOrderByIdAsc().getClub().getId();
//    }

}
