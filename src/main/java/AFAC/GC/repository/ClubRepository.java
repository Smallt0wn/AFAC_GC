package AFAC.GC.repository;

import AFAC.GC.constant.ClassifyClub;
import AFAC.GC.entity.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("SELECT c FROM Club c WHERE c.classifyClub = :classifyClub ORDER BY c.id DESC")
    Page<Club> findByClassifyClub(@Param("classifyClub") ClassifyClub classifyClub, Pageable pageable);


    Optional<Club> findById(Long id);

    Page<Club> findByClubNameContaining(Pageable pageable, String clubName);
    List<Club> findByClubNameContaining(String clubName);


}
