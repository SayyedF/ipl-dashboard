package com.jilani.ipldashboard.controller;

import com.jilani.ipldashboard.model.Match;
import com.jilani.ipldashboard.model.Team;
import com.jilani.ipldashboard.repository.MatchRepository;
import com.jilani.ipldashboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = teamRepository.getTeamByTeamName(teamName);
        team.setLatestMatches(matchRepository.getLatestMatches(teamName,4));
        return team;
    }

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getTeamMatchesByYear(@PathVariable String teamName, @RequestParam(defaultValue = "2020") Integer year) {
        //return matchRepository.getTeamMatchesByYear(teamName, year);
        return matchRepository.getMatchesByYear(teamName,year);
    }

    @GetMapping("/teams")
    public List<String> getTeamNames(){
        return teamRepository
                .findAll()
                .stream()
                .map(Team::getTeamName)
                .collect(Collectors.toList());
    }
}
