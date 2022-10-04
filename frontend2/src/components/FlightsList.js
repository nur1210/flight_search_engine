import {useEffect} from "react";
import RouteRow from "./RouteRow";


const FlightsList = ({flights}) => {

    return (
        <div>
            <h1>Available flights</h1>
            <table>
                <thead>
                <tr>
                    <th>Outbound</th>
                    <th>Return</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                {
                    flights.map((flight) => {
                        console.log(flight);
                        return <RouteRow key={flight.id} route={flight.route} price={flight.price}/>
                    })
                }
                </tbody>
            </table>
        </div>
    );
}

export default FlightsList;