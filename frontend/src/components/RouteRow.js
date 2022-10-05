import FlightDurationLabel from "./FlightDurationLabel";

const RouteRow = (props) => {
    return (
        <tr>
            {props.route.map((route) => {
                return (
                    <td key={route.flightNumber}>{route.departureAirport.iata}
                        <span className="arrow">→</span>
                        {route.arrivalAirport.iata}
                        <br/>
                        <FlightDurationLabel departureUTC={route.utcDepartureTime} arrivalUTC={route.utcArrivalTime}/>
                    </td>
                )
            })
            }
            <td>{props.price}</td>
        </tr>
    )
}
export default RouteRow;