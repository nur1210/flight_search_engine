import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftBox from "./SoftBox";
import SoftInput from "./SoftInput";
import {FormControl, FormControlLabel, FormLabel, Input, Radio, RadioGroup} from "@mui/material";
import SoftTypography from "./SoftTypography";


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
            <SoftBox
                sx={{
                    display: 'grid',
                    gap: 1,
                    gridTemplateColumns: 'repeat(8, 1fr)',
                    gridTemplateRows: 'auto',
                    gridTemplateAreas: `"header header header header header header header"
                                        "sidebar main main main main main main"
                                        "footer footer footer footer footer footer footer"`,
                }}
            >
                <SoftBox sx={{gridArea: "sidebar", alignContent: "left"}}>
                    <Row>
                        <Col>
                            <Popup props={params}/>
                        </Col>
                        <Col>
                            <FormControl sx={{marginTop: 4}}>
                                <SoftTypography>
                                    Filters
                                </SoftTypography>
                                <SoftTypography fontSize={12}>Stops</SoftTypography>
                                <RadioGroup
                                    aria-labelledby="demo-radio-buttons-group-label"
                                    defaultValue="0"
                                    name="radio-buttons-group"
                                    sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}
                                >
                                    <FormControlLabel value="0" control={<Radio />} label="Direct" />
                                    <FormControlLabel value="1" control={<Radio />} label="One stop" />
                                    <FormControlLabel value="2" control={<Radio />} label="Two stops" />
                                </RadioGroup>
                            </FormControl>
                        </Col>
                    </Row>
                </SoftBox>
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
                <SoftBox sx={{gridArea: "main"}}>
                    {
                        flights?.length
                            ? <FlightsList flights={flights}/>
                            : <p>No flights</p>
                    }
                </SoftBox>
            </SoftBox>
        </BasicLayout>
    );
}

export default SearchResults;