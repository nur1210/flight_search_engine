import BasicTable from "./RouteRow";
import {Table, TableCell, TableContainer } from "@mui/material";


const FlightsList = ({flights}) => {
    console.log(flights);
    if (flights.length === 0) {
        return null;
    } else {
        return (
            <div className={"container-sm w-75"}>
                <div className="my-2 card">
                    <div className="card-body">
                        <div className={"justify-content-center align-content-center"}>
                            <TableContainer>
                                <Table sx={{minWidth: 600}} aria-label="simple table">
                                    <thead>
                                    <tr>
                                        <TableCell align={"center"}>Outbound</TableCell>
                                        {flights[0].route.length > 1
                                            ? <TableCell align={"center"}>Return</TableCell>
                                            : null}
                                        <TableCell align={"center"}>Price</TableCell>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {
                                        flights.map((flight, i) => {
                                            console.log(flight);
                                            return <BasicTable key={i} route={flight.route} price={flight.price}
                                                               link={flight.deepLink}/>
                                        })
                                    }
                                    </tbody>
                                </Table>
                            </TableContainer>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default FlightsList;