package com.jilani.ipldashboard.repository;

import com.jilani.ipldashboard.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team getTeamByTeamName(String teamName);
}
