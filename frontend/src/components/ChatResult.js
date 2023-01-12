import TequilaService from "../services/TequilaService";
import {useEffect, useState} from "react";

import {Card, CardContent, Grid} from "@mui/material";
import SoftTypography from "./SoftTypography";
import SoftBox from "./SoftBox";

const ChatResult = ({steps, airport}) => {
    const {destination, month, duration, passengers, maxStopovers} = steps;
    const [flightsList, setFlightsList] = useState([]);

    useEffect(() => {
        getFlights();
    }, [])


    const getFlights = async () => {
        const monthRange = getMonthRange(month.value);
        const durationRange = durationDictionary[duration.value];

        const response = await TequilaService.getAdvanceSearchFlights(
            airport.iata,
            destination.value,
            monthRange.minDate,
            monthRange.maxDate,
            durationRange[0],
            durationRange[1],
            'round',
            passengers.value,
            'M',
            maxStopovers.value
        );
        setFlightsList(response.data.flights);
    }

    const durationDictionary = {
        1: [1, 3],
        2: [3, 7],
        3: [7, 14],
    };

    const getMonthRange = month => {
        const currentYear = new Date().getFullYear();
        const date = new Date(currentYear, month, 1);
        if (date < new Date()) {
            date.setFullYear(date.getFullYear() + 1);
        }
        const minDate = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
        const maxDate = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        return {
            minDate,
            maxDate: `${maxDate.getFullYear()}-${maxDate.getMonth() + 1}-${maxDate.getDate()}`,
        };
    };

    const redirectToBooking = url => {
        window.open(url, "_blank");
    }

    const timestampToFormattedTime = (timestamp) => {
        return new Date(timestamp).toLocaleDateString(
            navigator.language, {
                day: '2-digit',
                month: '2-digit',
                year: '2-digit',
            }
        )
    }


    return flightsList.length > 0 ? (
        <Grid>
            {
                flightsList.map((flight, i) => (
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
                                        gridColumn: '1 / 6'
                                    }}
                                >
                                    <CardContent sx={{paddingTop: "24px"}}>
                                        <Grid container direction="row">
                                            <Grid item xs={12}
                                                  sx={{
                                                      display: "flex",
                                                      justifyContent: "space-between",
                                                      alignItems: "center"
                                                  }}
                                            >
                                                <Grid item xs={8} margin={1}>
                                                    <SoftTypography variant="body2" component="div" fontSize={10}>
                                                        From:
                                                    </SoftTypography>
                                                    <SoftTypography variant="body" component="div"
                                                                    fontWeight={"medium"} fontSize={14}>
                                                        {timestampToFormattedTime(flight.outboundRoutes[0].localDepartureTime)}
                                                    </SoftTypography>
                                                    <SoftTypography variant="body2" component="div" fontSize={10}>
                                                        {flight.outboundRoutes[0].departureAirport.city}
                                                    </SoftTypography>
                                                </Grid>
                                                <Grid item xs={8} margin={1}>
                                                    <SoftTypography variant="body2" component="div" fontSize={10}>
                                                        To:
                                                    </SoftTypography>
                                                    <SoftTypography variant="body" component="div"
                                                                    fontWeight={"medium"} fontSize={14}>
                                                        {timestampToFormattedTime(flight.returnRoutes[0].localArrivalTime)}
                                                    </SoftTypography>
                                                    <SoftTypography variant="body2" component="div" fontSize={10}>
                                                        {flight.outboundRoutes.at(-1).arrivalAirport.city}
                                                    </SoftTypography>
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                    </CardContent>
                                </SoftBox>
                                <SoftBox
                                    sx={{
                                        gridRow: '1',
                                        gridColumn: '6 / 8',
                                        display: "flex",
                                        justifyContent: "start",
                                        alignItems: "center",
                                        verticalAlign: "middle"
                                    }}
                                >
                                    <Grid item xs={6}>
                                        <SoftTypography variant="body" component="div" fontSize={14} fontWeight={"bold"}>
                                            {flight.price + '€'}
                                        </SoftTypography>
                                    </Grid>
                                </SoftBox>
                            </SoftBox>
                        </Card>
                    )
                )
            }
        </Grid>
    ) : (

        <SoftTypography variant="h5" component="div" gutterBottom>
            No flights found
        </SoftTypography>

    );
}

export default ChatResult