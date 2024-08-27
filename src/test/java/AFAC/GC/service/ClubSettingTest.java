package AFAC.GC.service;

import AFAC.GC.constant.ClassifyClub;
import AFAC.GC.dto.ClubFormDto;
import AFAC.GC.entity.Club;
import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.repository.ClubLogoImgRepository;
import AFAC.GC.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

public class ClubSettingTest {

    @Autowired
    ClubService clubService;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubLogoImgRepository clubLogoImgRepository;

    List<MultipartFile> createMultipartfiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0; i<5; i++){
            String path = "C:/project/AIIA/GC/src/main/resources/img";
            String imageName = "test" + i + ".jpg";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(mockMultipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "test@gachon.ac.kr", roles = "ADMIN")
    void saveLogoTest() throws Exception {

        ClubFormDto clubFormDto = new ClubFormDto();
        clubFormDto.setClubName("testClub");
        clubFormDto.setDescription("testDescription");
        clubFormDto.setClassifyClub(ClassifyClub.CentralClub);
        clubFormDto.setContactNumber("01012345678");
        clubFormDto.setSnsLink("testlink");


        //given
        List<MultipartFile> multipartFileList = createMultipartfiles();
        Long clubId = clubService.saveLogo(clubFormDto, multipartFileList, 1L);
        List<ClubLogoImg> clubLogoImgList = clubLogoImgRepository.findByClubIdOrderByIdAsc(clubId);

        Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);

        assertEquals(clubFormDto.getClubName(), club.getClubName());
        assertEquals(clubFormDto.getDescription(), club.getDescription());
        assertEquals(clubFormDto.getClassifyClub(), club.getClassifyClub());
        assertEquals(clubFormDto.getContactNumber(), club.getContactNumber());
        assertEquals(clubFormDto.getSnsLink(), club.getSnsLink());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), clubLogoImgList.get(0).getOriImgName());
    }


}


