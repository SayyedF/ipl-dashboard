import {React, useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {MatchDetailCard} from "../components/MatchDetailCard";
import {MatchSmallCard} from "../components/MatchSmallCard";
import "./TeamPage.scss";
import { PieChart } from 'react-minimal-pie-chart';

export const TeamPage = () => {

    const [team, setTeam] = useState({latestMatches: []});
    const {teamName} = useParams();

    useEffect(() => {
        const fetchMatches = async () => {
                const response = await fetch(`/teams/${teamName}`);
                const data = await response.json();
                setTeam(data);
            };
        fetchMatches();
        },[teamName]
    );

    if(!team || !team.teamName) {
        return <h1>404 Team Not Found!</h1>
    }

    const wonMatches = team.totalWins;
    const lostMatches = team.totalMatches - wonMatches;


    return (
        <div className="TeamPage">
            <div className="team-name-section"><h1 className="team-name">{team.teamName}</h1></div>
            <div className="win-loss-section">Wins / Losses
                <PieChart
                    data={[
                        { title: 'Losses', value: lostMatches, color: '#a34d5d' },
                        { title: 'Wins', value: wonMatches, color: '#4da375' },
                    ]}
                />
            </div>
            <div className="match-detail-section">
                <h3>Latest Matches</h3>
                <MatchDetailCard teamName={team.teamName} match={team.latestMatches[0]}/>
            </div>
            {team.latestMatches.slice(1).map(match => <MatchSmallCard key={match.id} teamName={team.teamName} match={match} />)}
            <div className="more-link"><Link to={`/teams/${teamName}/matches/2020`}>More ></Link></div>
        </div>
    );
}