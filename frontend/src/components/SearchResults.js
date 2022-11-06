import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import DepartureDateInput from "./DepartureDateInput";
import {Col, Row} from "react-bootstrap";

function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [departureDate, setDepartureDate] = useState();
    const [returnDate, setReturnDate] = useState();
    const [flights, setFlights] = useState();
    const params = Object.fromEntries(searchParams.entries());


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
        params.departureDate = departureDate;
        setSearchParams(params);
        getFlights().then(r => {
            setFlights(r);
        });
    }, [setDepartureDate]);


    useEffect(() => {
        console.log(searchParams)
        params.returnDate = returnDate;
        setSearchParams(params);
        getFlights().then(r => {
            setFlights(r);
        });
    }, [setReturnDate]);


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