package AFAC.GC.service;


import AFAC.GC.dto.ClubFormDto;
import AFAC.GC.dto.ClubLogoImgDto;
import AFAC.GC.entity.Club;
import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.entity.Member;
import AFAC.GC.repository.ClubLogoImgRepository;
import AFAC.GC.repository.ClubRepository;
import AFAC.GC.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class ClubService {

    private final ClubLogoImgService clubLogoImgService;

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public long saveLogo(ClubFormDto clubFormDto, List<MultipartFile> ClubLogoImgFile, Long memberId) throws Exception{

        Member member = memberRepository.findById(memberId).get();


        Club club = Club.createClub(clubFormDto, member);
        clubRepository.save(club);

        ClubLogoImg clubLogoImg = new ClubLogoImg();
        clubLogoImg.setClub(club);


        clubLogoImgService.saveClubLogoImg(clubLogoImg, ClubLogoImgFile.get(0));

        return club.getId();
    }

}
