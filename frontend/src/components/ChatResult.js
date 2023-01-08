import TequilaService from "../services/TequilaService";
import {useEffect, useState} from "react";
import SoftButton from "./SoftButton";
import FlightsList from "./FlightsList";
import {Card, CardContent} from "@mui/material";
import SoftTypography from "./SoftTypography";

const ChatResult = ({steps, setFlights, triggerNextStep, flights}) => {
    const {destination, month, duration, passengers, maxStopovers} = steps;

    useEffect(() => {
        getFlights();
    }, [])

    useEffect(() => {
        if (flights.length > 0) {
            triggerNextStep({trigger: '15'})
        }
    }, [flights]);

    const getFlights = async () => {
        const monthRange = getMonthRange(month.value);
        const durationRange = durationDictionary[duration.value];

        const response = await TequilaService.getAdvanceSearchFlights(
            "EIN",
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

        setFlights(response.data.flights);
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


/*    return loading ? null : flights.length > 0 ? (
        <FlightsList flights={flights} />
    ) : (
        <Card>
            <CardContent>
                <SoftTypography variant="h5" component="div" gutterBottom>
                    No flights found
                </SoftTypography>
            </CardContent>
        </Card>
    );*/
/*    return flights.length > 0 ? (
        <FlightsList flights={flights}/>
    ) : (
        <Card>
            <CardContent>
                <SoftTypography variant="h5" component="div" gutterBottom>
                    No flights found
                </SoftTypography>
            </CardContent>
        </Card>
    );*/
}

export default ChatResult