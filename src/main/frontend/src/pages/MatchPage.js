import {React, useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {MatchDetailCard} from "../components/MatchDetailCard";
import {YearSelector} from "../components/YearSelector";
import "./MatchPage.scss";

export const MatchPage = () => {

    const [matches, setMatches] = useState([]);
    const {teamName, year} = useParams();

    useEffect(() => {
            const fetchMatches = async () => {
                const response = await fetch(`/teams/${teamName}/matches?year=${year}`);
                const data = await response.json();
                setMatches(data);
            };
            fetchMatches();
        },[teamName,year]
    );

    return (
        <div className="MatchPage">
            <div className="year-selector">
                <YearSelector teamName={teamName}/>
            </div>
            <div className="match-list">
                <h1>{teamName} matches in {year}</h1>
                {matches.map(match => <MatchDetailCard key={match.id} teamName={teamName} match={match} />)}
            </div>
        </div>
    );
}