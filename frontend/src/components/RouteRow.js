import FlightDurationLabel from "./FlightDurationLabel";
import { TableCell, TableRow } from "@mui/material";

export default function BasicTable(props) {
    return (
            <TableRow
                type={"button"}
                onClick={() => redirectToBooking(props.link)}
                key={props.id}
                sx={{'&:last-child td, &:last-child th': {border: 0}}}
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
            </TableRow>
    );
}

const redirectToBooking = url => {
    window.open(url, "_blank");
}