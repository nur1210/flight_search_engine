import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";



function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const params = Object.fromEntries(searchParams.entries());
    console.log(params);
    const [departureDate, setDepartureDate] = useState(params.Departure);
    const [returnDate, setReturnDate] = useState(params.Return);
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


/*    useEffect(() => {
        console.log(params);
        params.Departure = departureDate;
        setSearchParams(params);
    }, [departureDate]);


    useEffect(() => {
        console.log(params);
        params.Return = returnDate;
        setSearchParams(params);
    }, [returnDate]);*/


/*    useEffect(() => {
        console.log(params)
        getFlights().then(r => {
            setFlights(r);
        });
    }, [searchParams]);*/


    const getFlights = async () => {
        console.log(params);
        try {
            const response = await tequilaService.getAllFlights(
                params.flyFrom,
                params.flyTo,
                params.dateFrom,
                params.returnFrom,
                params.flightType,
                params.adults,
                params.children ? params.children : 0,
                params.infants ? params.infants : 0,
                params.selectedCabins);
            console.log(response);
            return response.data.flights;
        } catch (e) {
            console.log(e);
        }
    }


    return (
        <BasicLayout title={"Search Results"}>
            <Row>
                <Col md={4}>
                    <Popup props={params}/>
                </Col>
            </Row>
            {/*            <Row className={"justify-content-md-center"}>
                <Col xs lg="2">
                    <DepartureDateInput onChange={setDepartureDate} title={"Departure date"}/>
                </Col>
                <Col xs lg="2">
                    {
                        !params.flightType === "oneway"
                            ? <DepartureDateInput onChange={setReturnDate} title={"Arrival date"} disabled={true}/>
                            : <DepartureDateInput onChange={setReturnDate} title={"Arrival date"} disabled={false}/>
                    }
                </Col>
            </Row>*/}
            {
                flights?.length
                    ? <FlightsList flights={flights}/>
                    : <p>No flights</p>
            }
        </BasicLayout>
    );
}

export default SearchResults;