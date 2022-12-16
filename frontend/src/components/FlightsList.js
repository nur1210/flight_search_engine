import BasicTable from "./RouteRow";
import {
    Card, CardContent,
    Container,
    Grid,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import SoftTypography from "./SoftTypography";
import {CardBody} from "reactstrap";


const FlightsList = ({flights}) => {

    if (flights.length === 0) {
        return null;
    } else {
        return (
            <Container>
                <Card>
                    <CardContent>
                        <Grid sx={{justifyContent: "center", alignContent: "center"}}>
                            <TableContainer component={Paper}>
                                <Table>
                                    <TableHead sx={{ display: "table-header-group" }}>
                                    <TableRow>
                                        <TableCell align={"center"}>
                                            <SoftTypography fontWeight={"bold"} fontSize={16}>
                                                Outbound
                                            </SoftTypography>
                                        </TableCell>
                                        {flights[0].route.length > 1
                                            ? <TableCell align={"center"}>
                                                <SoftTypography fontWeight={"bold"} fontSize={16}>
                                                    Return
                                                </SoftTypography>
                                            </TableCell>
                                            : null}
                                        <TableCell align={"center"}>
                                            <SoftTypography fontWeight={"bold"} fontSize={16}>
                                                Price
                                            </SoftTypography>
                                        </TableCell>
                                    </TableRow>
                                    </TableHead>
                                    <TableBody>
                                    {
                                        flights.map((flight, i) => {
                                            return <BasicTable key={i} route={flight.route} price={flight.price}
                                                               link={flight.deepLink}/>
                                        })
                                    }
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Grid>
                    </CardContent>
                </Card>
            </Container>
        );
    }
}

export default FlightsList;