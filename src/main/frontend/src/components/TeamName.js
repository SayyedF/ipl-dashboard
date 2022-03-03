import {React} from "react";
import "./TeamName.scss";

export const TeamName = ({teamName}) => {

    return(
        <div className="TeamName">
            <h2>{teamName}</h2>
        </div>
    );
}