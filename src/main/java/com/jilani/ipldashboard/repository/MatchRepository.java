package com.jilani.ipldashboard.repository;

import com.fasterxml.jackson.databind.DatabindException;
import com.jilani.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    List<Match> findAllByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    List<Match> findAllByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
            String team1, LocalDate startDate1, LocalDate endDate1,
            String team2, LocalDate startDate2, LocalDate endDate2
            );

    /*
    Another way to pass query param:
        @Query... where m.team1 = :teamName
        ........method(@Param("teamName") String teamName)
    */
    @Query("from Match m where (m.team1 = ?1 or m.team2 = ?1) and year(m.date) = ?2 order by m.date desc")
    List<Match> getMatchesByYear(String teamName, int year);

    default List<Match> getLatestMatches(String teamName, int pageSize){
        Pageable pageable = PageRequest.of(0, pageSize);
        List<Match> latestMatches = findAllByTeam1OrTeam2OrderByDateDesc(teamName, teamName,pageable);
        return  latestMatches;
    }

    default List<Match> getTeamMatchesByYear(String teamName, int year){
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year+1,1,1);
        List<Match> latestMatches = findAllByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
                teamName, startDate, endDate,
                teamName, startDate, endDate
        );
        return latestMatches;
    }
}
