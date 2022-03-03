import {React, useEffect, useState} from "react";
import './HomePage.scss';
import {Link} from "react-router-dom";
import {TeamName} from "../components/TeamName";

export const HomePage = () => {

    const [teams, setTeams] = useState([]);

    useEffect(() => {
            const fetchTeams = async () => {
                const response = await fetch(`/teams`);
                const data = await response.json();
                setTeams(data);
            };
            fetchTeams();
        },[]
    );

    return (
        <div className="HomePage">
            <div className="app-title">
                <h1>IPL Dashboard</h1>
            </div>
            <div className="team-list">
                {teams.map(team =>
                    <Link key={team} to={`/teams/${team}`}><TeamName key={team} teamName={team} /></Link>
                )}
            </div>
        </div>
    );
}
