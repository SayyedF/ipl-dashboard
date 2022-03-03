import {React} from "react";
import "./YearSelector.scss";
import {Link} from "react-router-dom";

export const YearSelector = ({teamName}) => {

    let years = [];
    const startYear = process.env.REACT_APP_DATA_START_DATE;
    const endYear = process.env.REACT_APP_DATA_END_DATE;

    for (let i = endYear; i >= startYear ; i--) {
        years.push(i);
    }

    return(
        <div className="YearSelector">
            <h4>Select a year</h4>
            <ul>
            {years.map(year =>
                <Link key={year} to={`/teams/${teamName}/matches/${year}`}><li className="year">{year}</li></Link>
            )}
            </ul>
        </div>
    );
}