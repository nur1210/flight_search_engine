import BasicTable from "./RouteRow";
import {
    Card, CardContent,
    Container,
    Grid,
} from "@mui/material";
import SoftTypography from "./SoftTypography";
import FlightDurationLabel from "./FlightDurationLabel";
import SoftBox from "./SoftBox";
import SoftButton from "./SoftButton";


const FlightsList = ({flights}) => {
    console.log(flights);

    const stops = (stops) => {
        switch (stops.length) {
            case 1:
                return 'Direct flight';
            case 2:
                return `1 Stop ${stops[0].arrivalAirport.city}`;
            case 3:
                return `2 stops ${stops[0].arrivalAirport.city}, ${stops[1].arrivalAirport.city}`;
        }
    }

    const timestampToFormattedTime = (timestamp) => {
        return new Date(timestamp).toLocaleTimeString(
            navigator.language, {
                hour: '2-digit',
                minute: '2-digit'
            }
        )
    }

    const redirectToBooking = url => {
        window.open(url, "_blank");
    }

    return flights.length === 0
        ? null
        : (
            <Container>
                {
                    flights.map((flight, i) => (
                            <Card
                                key={i}
                                sx={{
                                    marginTop: 2,
                                    marginBottom: 2,
                                    backgroundColor: "#ffffff",
                                    ':hover': {
                                        boxShadow: '0px 5px 10px rgba(0, 0, 0, 0.2)',
                                    },
                                }}
                                onClick={() => redirectToBooking(flight.deepLink)}
                            >
                                    <SoftBox
                                        sx={{
                                            display: 'grid',
                                            gridAutoColumns: '1fr',
                                            gap: 1,
                                        }}
                                    >
                                        <SoftBox
                                            sx={{
                                                gridRow: '1',
                                                gridColumn: '1 / 7'
                                            }}
                                        >
                                            <CardContent sx={{padding: 1, paddingTop: "24px"}}>
                                                <Grid container direction="row">
                                                    <Grid item xs={12}
                                                          sx={{
                                                              display: "flex",
                                                              justifyContent: "space-between",
                                                              alignItems: "center"
                                                          }}
                                                    >
                                                        <Grid item xs={6}>
                                                            <SoftTypography variant="h6" component="div">
                                                                {flight.outboundRoutes[0].airline}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={12}>
                                                            <SoftTypography variant="body" component="div"
                                                                            fontWeight={"medium"}>
                                                                {timestampToFormattedTime(flight.outboundRoutes[0].localDepartureTime)}
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {flight.outboundRoutes[0].departureAirport.city}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={8}>
                                                            <SoftTypography variant="body2" component="div">
                                                                <FlightDurationLabel
                                                                    departureUTC={flight.outboundRoutes[0].utcDepartureTime}
                                                                    arrivalUTC={flight.outboundRoutes.at(-1).utcArrivalTime}/>
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {stops(flight.outboundRoutes)}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={12}>
                                                            <SoftTypography variant="body" component="div"
                                                                            fontWeight={"medium"}>
                                                                {timestampToFormattedTime(flight.outboundRoutes[0].localArrivalTime)}
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {flight.outboundRoutes.at(-1).arrivalAirport.city}
                                                            </SoftTypography>
                                                        </Grid>
                                                    </Grid>
                                                </Grid>
                                            </CardContent>
                                            <CardContent sx={{padding: 1}}>
                                                <Grid container direction="row">
                                                    <Grid item xs={12}
                                                          sx={{
                                                              display: "flex",
                                                              justifyContent: "space-between",
                                                              alignItems: "center"
                                                          }}
                                                    >
                                                        <Grid item xs={6}>
                                                            <SoftTypography variant="h6" component="div">
                                                                {flight.returnRoutes[0].airline}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={12}>
                                                            <SoftTypography variant="body" component="div"
                                                                            fontWeight={"medium"}>
                                                                {timestampToFormattedTime(flight.returnRoutes[0].localDepartureTime)}
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {flight.returnRoutes[0].departureAirport.city}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={8}>
                                                            <SoftTypography variant="body2" component="div">
                                                                <FlightDurationLabel
                                                                    departureUTC={flight.returnRoutes[0].utcDepartureTime}
                                                                    arrivalUTC={flight.returnRoutes.at(-1).utcArrivalTime}/>
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {stops(flight.returnRoutes)}
                                                            </SoftTypography>
                                                        </Grid>
                                                        <Grid item xs={12}>
                                                            <SoftTypography variant="body" component="div"
                                                                            fontWeight={"medium"}>
                                                                {timestampToFormattedTime(flight.returnRoutes[0].localArrivalTime)}
                                                            </SoftTypography>
                                                            <SoftTypography variant="body2" component="div">
                                                                {flight.returnRoutes.at(-1).arrivalAirport.city}
                                                            </SoftTypography>
                                                        </Grid>
                                                    </Grid>
                                                </Grid>
                                            </CardContent>
                                        </SoftBox>
                                        <SoftBox
                                            sx={{
                                                gridRow: '1',
                                                gridColumn: '7 / 8',
                                                display: "flex",
                                                justifyContent: "start",
                                                alignItems: "center",
                                                verticalAlign: "middle"
                                            }}
                                        >
                                            <Grid item xs={8}>
                                                <SoftTypography variant="body" component="div" fontWeight={"bold"}>
                                                    {flight.price + '€'}
                                                </SoftTypography>
                                            </Grid>
                                        </SoftBox>
                                    </SoftBox>
                            </Card>
                        )
                    )
                }
            </Container>
        );
}

export default FlightsList;