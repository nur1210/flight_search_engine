import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftBox from "./SoftBox";
import SoftInput from "./SoftInput";
import {Card, FormControl, FormControlLabel, FormLabel, Input, Radio, RadioGroup} from "@mui/material";
import SoftTypography from "./SoftTypography";
import SidenavCard from "../examples/Sidenav/SidenavCard";


function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const params = Object.fromEntries(searchParams.entries());
    console.log(params);
    const [departureDate, setDepartureDate] = useState(params.dateFrom);
    const [returnDate, setReturnDate] = useState(params.returnFrom);
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
        params.dateFrom = departureDate;
        setSearchParams(params);
    }, [departureDate]);


    useEffect(() => {
        params.returnFrom = returnDate;
        setSearchParams(params);
    }, [returnDate]);


    useEffect(() => {
        console.log(params)
        getFlights().then(r => {
            setFlights(r);
        });
    }, [searchParams]);


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
                        <SoftBox sx={{marginTop: 4}}>
                            <Col>
                                <SoftTypography>
                                    Filters
                                </SoftTypography>
                                <SoftTypography fontSize={12}>Dates</SoftTypography>
                                <SoftInput
                                    type="date"
                                    value={departureDate}
                                    onChange={(e) => setDepartureDate(e.target.value)}
                                />
                                <SoftInput
                                    type="date"
                                    value={returnDate}
                                    onChange={(e) => setReturnDate(e.target.value)}
                                />
                                <FormControl sx={{marginTop: 2}}>
                                    <SoftTypography fontSize={12}>Stops</SoftTypography>
                                    <RadioGroup
                                        aria-labelledby="demo-radio-buttons-group-label"
                                        defaultValue="0"
                                        name="radio-buttons-group"
                                        sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}
                                    >
                                        <FormControlLabel value="0" control={<Radio/>} label="Direct"/>
                                        <FormControlLabel value="1" control={<Radio/>} label="One stop"/>
                                        <FormControlLabel value="2" control={<Radio/>} label="Two stops"/>
                                    </RadioGroup>
                                </FormControl>
                            </Col>
                        </SoftBox>
                    </Row>
                </SoftBox>
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