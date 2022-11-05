import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import DatesCard from "./DatesCard";
import {useSearchParams} from "react-router-dom";
import DepartureDateInput from "./DepartureDateInput";

function SearchResults() {
    const [searchParams] = useSearchParams();
    const [departureDate, setDepartureDate] = useState();
    const [returnDate, setReturnDate] = useState();
    const [flights, setFlights] = useState();
    // for (const entry of searchParams.entries()) {
    //     console.log(entry);
    // }


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const params = Object.fromEntries(searchParams.entries());
        console.log(params)
        const getFlights = async () => {
            try {
                const response = await tequilaService.getAllFlights(params.origin, params.destination,
                    params.departureDate, params.returnDate,
                    params.flightType, params.adults,
                    params.children, params.infants,
                    params.travelClass);

                isMounted && setFlights(response.data.flights);
                console.log(response.data.flights);
            } catch (e) {
                console.log(e);
            }
        }
        getFlights();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, []);



    return (
        <article>
{/*            <DepartureDateInput onChange={setDepartureDate} title={"Departure date"}/>
            <DepartureDateInput onChange={setReturnDate} title={"Arrival date"}/>*/}
            <h2>Search Results</h2>
            {
                flights?.length
                    ? <FlightsList flights={flights}/>
                    : <p>Loading...</p>
            }
        </article>
    );
}

export default SearchResults;