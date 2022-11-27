import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import Table from "../examples/Tables/Table";



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


    useEffect(() => {
        console.log(params);
        params.Departure = departureDate;
        setSearchParams(params);
    }, [departureDate]);


    useEffect(() => {
        console.log(params);
        params.Return = returnDate;
        setSearchParams(params);
    }, [returnDate]);


    useEffect(() => {
        console.log(params)
        getFlights().then(r => {
            setFlights(r);
        });
    }, [searchParams]);


    const getFlights = async () => {
        try {
            const response = await tequilaService.getAllFlights(params.Origin, params.Destination,
                params.Departure, params.Return,
                params.flightType, params.Adults,
                params.Children, params.Infants,
                params.travelClass);
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
{/*            {
                flights?.length
                    ? <Table
                        columns={[
                            {name: "outbound", align: "left"},
                            {name: "return", align: "left"},
                            {name: "dates", align: "left"},
                            {name: "price", align: "center"},
                        ]}
                        rows={[
                            flights.map((flight) => {
                                return (
                                    {
                                        outbound: flight.route[0].arrivalAirport.iata,
                                        return: flight.route[0].arrivalAirport.iata,
                                        dates: flight.route[0].utcDepartureTime + " - " + flight.route[0].utcArrivalTime,
                                        price: flight.price,
                                    }
                                )
                            })
                        ]}
                    />
                    : <p>No flights</p>
            }*/}
        </BasicLayout>
    );
}

export default SearchResults;