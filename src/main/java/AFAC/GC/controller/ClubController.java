package AFAC.GC.controller;

import AFAC.GC.constant.ClassifyClub;
import AFAC.GC.dto.ClubFormDto;
import AFAC.GC.dto.ClubLogoImgDto;
import AFAC.GC.entity.Club;
import AFAC.GC.entity.ClubLogoImg;
import AFAC.GC.entity.Member;
import AFAC.GC.repository.ClubLogoImgRepository;
import AFAC.GC.repository.ClubRepository;
import AFAC.GC.repository.MemberRepository;
import AFAC.GC.service.ClubLogoImgService;
import AFAC.GC.service.ClubService;
import AFAC.GC.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final MemberService memberService;


    @GetMapping(value = "/club/new")
    public String clubRegistration(Model model){
        model.addAttribute("clubFormDto", new ClubFormDto());
        return "club/ClubRegistration";
    }

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubLogoImgRepository ClubLogoImgRepository;

    @GetMapping("/club/ClubPage")
    public String getClubList(Model model, @PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 6) Pageable pageable) {

        Page<Club> list = clubRepository.findAll(pageable);

        model.addAttribute("clubs", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "club/ClubPage";
    }

    @GetMapping("/club/ClubSearchPage")
    public String getClubListContaining(Model model, String clubName, @PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 6) Pageable pageable) {

        Page<Club> list = clubRepository.findByClubNameContaining(pageable, clubName);
        List<Club> isEmpty=clubRepository.findByClubNameContaining(clubName);

        if(isEmpty.isEmpty()){
            model.addAttribute("errorMessage", "검색 결과가 없습니다.");
        }
            model.addAttribute("clubs", list);
            model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
            model.addAttribute("next", pageable.next().getPageNumber());
            model.addAttribute("hasNext", list.hasNext());
            model.addAttribute("hasPrev", list.hasPrevious());

        return "club/ClubSearchPage";
    }

    @GetMapping("/club/ClubPageSort")
    public String getClubList(Model model,
                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 6) Pageable pageable,
                              @RequestParam(value = "classifyClub", required = false) ClassifyClub classifyClub) {
        Page<Club> list;
        if (classifyClub != null) {
            list = clubRepository.findByClassifyClub(classifyClub, pageable);
        } else {
            list = clubRepository.findAll(pageable);
        }

        model.addAttribute("clubs", list.getContent());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        model.addAttribute("selectedClassifyClub", classifyClub);

        return "club/ClubPageSort";
    }

    @GetMapping("/club/ClubContent/{id}")
    public String getClubById(@PathVariable("id") Long id, Model model) {
        Optional<Club> club = clubRepository.findById(id);
        Optional<ClubLogoImg> clubLogoImg = ClubLogoImgRepository.findById(id);

        model.addAttribute("imgUrl", clubLogoImg.get().getImgUrl());
        if (club.isPresent()) {
            model.addAttribute("club", club.get());
            return "club/ClubContent";
        } else {
            return "/MainPage"; // 클럽을 찾지 못했을 때 404 페이지로 이동
        }
    }




    @GetMapping("/club/ClubRegistration")
    public String clubSetting(){return "club/ClubRegistration"; }

    @PostMapping(value = "/club/new")
    public String clubNew(@Valid ClubFormDto clubFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("clubLogoImgFileList") List<MultipartFile> clubLogoImgFileList,
                          @AuthenticationPrincipal User currentUser) {
        if (bindingResult.hasErrors()) {
            return "club/ClubRegistration";
        }

        if (clubLogoImgFileList.get(0).isEmpty() && clubFormDto.getId() == null) {
            model.addAttribute("errorMessage", "로고 이미지는 필수 입력 값입니다.");
            return "club/ClubRegistration";
        }

        try {
            Member member = memberService.findBy(currentUser.getUsername());
            clubService.saveClubWithLogo(clubFormDto, clubLogoImgFileList, member.getId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "동아리 등록 중 오류가 발생했습니다.");
            return "club/ClubRegistration";
        }

        return "redirect:/club/list";    }

}

