import FlightDurationLabel from "./FlightDurationLabel";
import {TableCell, TableRow} from "@mui/material";
import SoftTypography from "./SoftTypography";

export default function BasicTable(props) {
    return (
        <TableRow
            onClick={() => redirectToBooking(props.link)}
            key={props.id}
        >
            {props.route.map((route) => (
                <TableCell align={"center"}>
                    <SoftTypography fontSize={14} fontWeight={"medium"}>Airline: {route.airline}</SoftTypography>
                    <SoftTypography fontSize={14} fontWeight={"medium"}>{route.departureAirport.iata}
                        <span className="arrow">→</span>
                        {route.arrivalAirport.iata}</SoftTypography>
                    <FlightDurationLabel departureUTC={route.utcDepartureTime} arrivalUTC={route.utcArrivalTime}/>
                </TableCell>
            ))}
            <TableCell align={"center"}>
                <SoftTypography fontSize={14} fontWeight={"medium"}>
                    {props.price}
                </SoftTypography>
            </TableCell>
        </TableRow>
    );
}

const redirectToBooking = url => {
    window.open(url, "_blank");
}