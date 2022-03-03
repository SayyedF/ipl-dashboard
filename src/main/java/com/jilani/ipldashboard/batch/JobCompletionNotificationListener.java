package com.jilani.ipldashboard.batch;

import com.jilani.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            //Populate teams
            Map<String, Team> teamData = new HashMap<>();

            //find team names in team1 coulmn
            em.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            //find team names in team2 coulmn
            em.createQuery("select distinct m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        if(teamData.containsKey((String) e[0])) {
                            Team team = teamData.get((String) e[0]);
                            team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                        }
                    });

            //find number of times a team was the winner
            //way 1:
//            teamData.keySet().forEach(teamName -> {
//                TypedQuery<Long> q = em.createQuery("select count(*) from Match m where m.winner = ?0", Long.class);
//                q.setParameter(0, teamName);
//                long totalWins = q.getSingleResult();
//                teamData.get(teamName).setTotalWins(totalWins);
//            });

            //way 2:
            em.createQuery("select distinct m.winner, count(*) from Match m group by m.winner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        if(teamData.containsKey((String) e[0])) {
                            Team team = teamData.get((String) e[0]);
                            team.setTotalWins((long) e[1]);
                        }
                    });

            //save teamData in the db
            teamData.values()
                    .forEach(team -> {
                        em.persist(team);
                        System.out.println(team);
                    });


            /*jdbcTemplate.query("SELECT first_name, last_name FROM people",
                    (rs, row) -> new Person(
                            rs.getString(1),
                            rs.getString(2))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));*/
        }
    }
}