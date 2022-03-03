import React from 'react';
import {Link} from "react-router-dom";
import './MatchSmallCard.scss';

export const MatchSmallCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
    const isMatchWinner = teamName === match.winner;
    return (
        <div className={isMatchWinner ? "MatchSmallCard won-card" : "MatchSmallCard lost-card"}>
            <span>vs </span>
            <h1><Link to={`/teams/${otherTeam}`}>{otherTeam}</Link></h1>

            <p className="match-result">{match.winner} won by {match.resultMargin} {match.result}</p>
        </div>
    );
}