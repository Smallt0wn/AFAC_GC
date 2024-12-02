package AFAC.GC.service;

import AFAC.GC.dto.ClubFormDto;
import AFAC.GC.entity.Club;
import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.entity.Member;
import AFAC.GC.repository.ClubRepository;
import AFAC.GC.repository.MemberRepository;
import AFAC.GC.service.ClubLogoImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class ClubService {

    private final ClubLogoImgService clubLogoImgService;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public long saveClubWithLogo(ClubFormDto clubFormDto, List<MultipartFile> clubLogoImgFiles, Long memberId) throws Exception {
        // 1. Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        // 2. Club 생성 및 저장
        Club club = Club.builder()
                .clubName(clubFormDto.getClubName())
                .classifyClub(clubFormDto.getClassifyClub())
                .description(clubFormDto.getDescription())
                .snsLink(clubFormDto.getSnsLink())
                .contactNumber(clubFormDto.getContactNumber())
                .build();

        club.assignMember(member);

        clubRepository.save(club);

        // 3. ClubLogoImg 검증 및 생성
        if (clubLogoImgFiles == null || clubLogoImgFiles.isEmpty()) {
            throw new IllegalArgumentException("ClubLogoImgFiles cannot be null or empty");
        }

        ClubLogoImg clubLogoImg = ClubLogoImg.builder()
                .club(club)
                .build();

        // 4. 로고 이미지 저장
        clubLogoImgService.saveClubLogoImg(clubLogoImg, clubLogoImgFiles.get(0));

        return club.getId();
    }
}
