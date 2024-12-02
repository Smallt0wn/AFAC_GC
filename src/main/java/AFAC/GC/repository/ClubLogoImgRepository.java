package AFAC.GC.repository;


import AFAC.GC.entity.ClubLogoImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubLogoImgRepository extends JpaRepository<ClubLogoImg, Long> {
    List<ClubLogoImg> findByClubIdOrderByIdAsc(Long clubId);

}

