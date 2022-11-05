import FlightDurationLabel from "./FlightDurationLabel";
import { TableCell, TableRow } from "@mui/material";

export default function BasicTable(props) {
    return (
            <tr
                onClick={() => redirectToBooking(props.link)}
                key={props.id}
            >
                {props.route.map((route) => (
                    <TableCell align={"center"}>
                        <label>Airline: {route.airline}</label>
                        <br/>
                        {route.departureAirport.iata}
                        <span className="arrow">→</span>
                        {route.arrivalAirport.iata}
                        <br/>
                        <FlightDurationLabel departureUTC={route.utcDepartureTime} arrivalUTC={route.utcArrivalTime}/>
                    </TableCell>
                ))}
                <TableCell align={"center"}>{props.price}</TableCell>
            </tr>
    );
}

const redirectToBooking = url => {
    window.open(url, "_blank");
}