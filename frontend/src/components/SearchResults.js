import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import DepartureDateInput from "./DepartureDateInput";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import Table from "../examples/Tables/Table";
import SoftBox from "./SoftBox";
import SoftBadge from "./SoftBadge";
import BasicTable from "./RouteRow";


function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const params = Object.fromEntries(searchParams.entries());
    console.log(params);
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
    }, [departureDate]);


    useEffect(() => {
        console.log(params);
        params.returnDate = returnDate;
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
        <BasicLayout>
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
            <h2>Search Results</h2>
            {
                flights?.length
                    ? <FlightsList flights={flights}/>
                    : <p>No flights</p>
            }
            {
                flights?.length
                    ?   <Table
                        columns={[
                            {name: "Outbound", align: "left"},
                            {name: "Return", align: "left"},
                            {name: "Dates", align: "left"},
                            {name: "Price", align: "center"},
                        ]}
                        rows={[
                                flights.map((flight, i) => {
                                    {
                                        return(
                                            {
                                                name: ["https://bit.ly/3I3pbe6", "John Micheal"],
                                                function: "Manager",
                                                review: (
                                                    <SoftBox ml={-1.325}>
                                                        <SoftBadge size="small" badgeContent="positive"/>
                                                    </SoftBox>
                                                ),
                                                email: "john@user.com",
                                                employed: "23/04/18",
                                            }
                                        )
                                    }
                            })
                        ,
                        {
                            name: ["https://bit.ly/3I3pbe6", "John Micheal"],
                            function: "Manager",
                            review: (
                            <SoftBox ml={-1.325}>
                            <SoftBadge size="small" badgeContent="positive"/>
                            </SoftBox>
                            ),
                            email: "john@user.com",
                            employed: "23/04/18",
                        }

                            ]}
                    />
                    : <p>No flights</p>
            }

        </BasicLayout>
    );
}

export default SearchResults;