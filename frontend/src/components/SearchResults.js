import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import DepartureDateInput from "./DepartureDateInput";
import {Col, Row} from "react-bootstrap";

function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const params = Object.fromEntries(searchParams.entries());
    const [departureDate, setDepartureDate] = useState(params.departureDate);
    const [returnDate, setReturnDate] = useState(params.returnDate);
    const [flights, setFlights] = useState();


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        getFlights().then(r => {
            if (isMounted) {
                setFlights(r);
            }
        });

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, []);


    useEffect(() => {
        console.log(params);
        params.departureDate = departureDate;
        setSearchParams(params);
/*        getFlights().then(r => {
            setFlights(r);
        });*/
    }, [departureDate]);


    useEffect(() => {
        console.log(params);
        params.returnDate = returnDate;
        setSearchParams(params);
/*        getFlights().then(r => {
            setFlights(r);
        });*/
    }, [returnDate]);


    useEffect(() => {
        console.log(params)
        getFlights().then(r => {
            setFlights(r);
        });
    }, [searchParams]);


    const getFlights = async () => {
        try {
            const response = await tequilaService.getAllFlights(params.origin, params.destination,
                params.departureDate, params.returnDate,
                params.flightType, params.adults,
                params.children, params.infants,
                params.travelClass);
            return response.data.flights;
        } catch (e) {
            console.log(e);
        }
    }


    return (
        <article>
            <Row className={"justify-content-md-center"}>
                <Col xs lg="2">
                    <DepartureDateInput onChange={setDepartureDate} title={"Departure date"}/>
                </Col>
                <Col xs lg="2">
                    <DepartureDateInput onChange={setReturnDate} title={"Arrival date"}/>
                </Col>
            </Row>
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