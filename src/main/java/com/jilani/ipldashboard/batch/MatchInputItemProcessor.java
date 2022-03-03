package com.jilani.ipldashboard.batch;

import com.jilani.ipldashboard.data.MatchInput;
import com.jilani.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MatchInputItemProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchInputItemProcessor.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public Match process(final MatchInput input) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(input.getId()));
        match.setCity(input.getCity());
        match.setDate(LocalDate.parse(input.getDate(), formatter));
        match.setPlayerOfMatch(input.getPlayerOfMatch());
        match.setVenue(input.getVenue());

        String firstInningsTeam;
        String secondInningsTeam;

        if("bat".equals(input.getTossDecision())) {
            firstInningsTeam = input.getTossWinner();
            secondInningsTeam = firstInningsTeam.equals(input.getTeam1()) ? input.getTeam2() : input.getTeam1();
        } else {
            secondInningsTeam = input.getTossWinner();
            firstInningsTeam = secondInningsTeam.equals(input.getTeam1()) ? input.getTeam2() : input.getTeam1();
        }

        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);

        match.setTossWinner(input.getTossWinner());
        match.setTossDecision(input.getTossDecision());
        match.setWinner(input.getWinner());
        match.setResult(input.getResult());
        match.setResultMargin(input.getResultMargin());
        match.setUmpire1(input.getUmpire1());
        match.setUmpire2(input.getUmpire2());

        return match;
    }

}