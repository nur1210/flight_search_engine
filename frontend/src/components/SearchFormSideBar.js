import SoftBox from "./SoftBox";
import FlightCard from "./FlightCard";
import tequilaService from "../services/TequilaService";
import {useEffect, useState} from "react";
import SoftTypography from "./SoftTypography";

const SearchFormSideBar = ({airport}) => {
    const [flights, setFlights] = useState([]);


    useEffect(() => {
        const month = new Date().getMonth() + 1;
        const day = new Date().getDate() + 1;
        const firstDate = `${new Date().getFullYear()}-${month < 10 ? `0${month}` : `${month}`}-${day < 10 ? `0${day}` : `${day}`}`;
        const lastDate = new Date().getFullYear() + '-' + month + '-' + new Date(new Date().getFullYear(), month, 0).getDate();

        const getFlights = async () => {
                try {
                    const response = await tequilaService.getTopThreeCheapestFlightsFromUserLocation(
                        airport.iata,
                        firstDate,
                        lastDate,
                        firstDate,
                        lastDate,
                        1,
                        7,
                        'round',
                        1,
                        'M');
                    console.log(response.data.flights);
                    setFlights(response.data.flights);
                } catch (e) {
                    console.log(e);
                }
            }
        ;
        getFlights();

    }, [airport]);

    return (
        <SoftBox sx={{gridArea: 'sidebar', marginTop: 3}}>
            {flights.length !== 0 ?
                <>
                    <SoftTypography fontWeight={"medium"}>
                        Cheapest flights from {airport.city}
                    </SoftTypography>
                    {flights.map((flight, i) => (
                        <FlightCard key={i} flight={flight}/>
                    ))}
                </>
                :
                null
            }
        </SoftBox>
    )
}

export default SearchFormSideBar;