import BasicTable from "./RouteRow";
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";


const FlightsList = ({flights}) => {
    if (flights.length === 0) {
        return null;
    } else {
        return (
            <div className={"justify-content-center align-content-center"}>
                <TableContainer>
                    <Table sx={{minWidth: 650}} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>Outbound</TableCell>
                                {flights[0].route.length > 1 ? <TableCell align={"center"}>Return</TableCell> : null}
                                <TableCell align={"center"}>Price</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                flights.map((flight) => {
                                    console.log(flight);
                                    return <BasicTable key={flight.id} route={flight.route} price={flight.price}
                                                       link={flight.deepLink}/>
                                })
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        );
    }
}

export default FlightsList;