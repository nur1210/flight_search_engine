import BasicTable from "./RouteRow";
import {Table, TableCell, TableContainer} from "@mui/material";
import SoftTypography from "./SoftTypography";


const FlightsList = ({flights}) => {
    if (flights.length === 0) {
        return null;
    } else {
        return (
            <div className={"container"}>
                <div className="my-2 card">
                    <div className="card-body">
                        <div className={"justify-content-center align-content-center"}>
                            <TableContainer>
                                <Table sx={{minWidth: 600}} aria-label="simple table">
                                    <thead>
                                    <tr>
                                        <TableCell align={"center"}>
                                            <SoftTypography fontWeight={"medium"} fontSize={16}>
                                                Outbound
                                            </SoftTypography>
                                        </TableCell>
                                        {flights[0].route.length > 1
                                            ? <TableCell align={"center"}>
                                                <SoftTypography fontWeight={"medium"} fontSize={16}>
                                                    Return
                                                </SoftTypography>
                                            </TableCell>
                                            : null}
                                        <TableCell align={"center"}>
                                            <SoftTypography fontWeight={"medium"} fontSize={16}>
                                                Price
                                            </SoftTypography>
                                        </TableCell>
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