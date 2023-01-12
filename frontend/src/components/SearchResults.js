import React, {useEffect, useState} from 'react';
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import {useSearchParams} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import Popup from "./Popup";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftBox from "./SoftBox";
import SoftInput from "./SoftInput";
import {Card, CardContent, FormControl, FormControlLabel, Grid, Radio, RadioGroup} from "@mui/material";
import SoftTypography from "./SoftTypography";


function SearchResults() {
    const [searchParams, setSearchParams] = useSearchParams();
    const params = Object.fromEntries(searchParams.entries());
    console.log(params);
    const [departureDate, setDepartureDate] = useState(params.dateFrom);
    const [returnDate, setReturnDate] = useState(params.returnFrom);
    const [stops, setStops] = useState(params.maxSectorStopovers);
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
        params.maxSectorStopovers = stops;
        setSearchParams(params);
    }, [stops]);


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
                params.selectedCabins,
                params.maxSectorStopovers);
            console.log(response);
            return response.data.flights;
        } catch (e) {
            console.log(e);
        }
    }


    return (
        <BasicLayout
            light={false}
            title={"Search Results"}
            description={"Here you can find the best flights for your trip"}
        >
            <Grid
                container
                item xs={12} md={12} lg={12} xl={12}
                mt={-2}
                sx={{
                    display: 'grid',
                    gap: 1,
                    gridTemplateColumns: 'repeat(6, 1fr)',
                    gridTemplateRows: 'auto',
                    gridTemplateAreas: `"header header header header header"
                                        "sidebar main main main main"
                                        "footer footer footer footer footer"`,
                }}
            >
                <SoftBox
                    sx={{
                        gridArea: "sidebar",
                        display: 'flex',
                        flexDirection: 'column',
                        marginRight: 1,
                    }}
                >
                    <Grid gridRow>
                        <Grid gridColumn>
                            <Popup props={params}/>
                        </Grid>
                        <SoftBox
                            sx={{
                                marginTop: 3,
                                display: 'flex',
                                alignContent: 'right',
                            }}
                        >
                            <Grid gridColumn sx={{width: '100%'}}>
                                <Card sx={{
                                    backgroundColor: '#ffffff',
                                }}>
                                    <SoftTypography>
                                        Filters
                                    </SoftTypography>
                                    <CardContent>
                                        <SoftTypography fontSize={12}>Dates</SoftTypography>
                                        <SoftInput
                                            type="date"
                                            value={departureDate}
                                            onChange={(e) => setDepartureDate(e.target.value)}
                                            sx={{marginTop: 1}}
                                        />
                                        <SoftInput
                                            type="date"
                                            value={returnDate}
                                            onChange={(e) => setReturnDate(e.target.value)}
                                            sx={{marginTop: 2}}
                                        />
                                    </CardContent>
                                    <CardContent>
                                        <FormControl sx={{marginTop: 2, width: '100%'}}>
                                            <SoftTypography fontSize={12}>Stops</SoftTypography>
                                            <RadioGroup
                                                aria-labelledby="demo-radio-buttons-group-label"
                                                defaultValue={stops !== undefined ? stops : 0}
                                                name="radio-buttons-group"
                                                onChange={(e) => setStops(e.target.value)}
                                                sx={{display: 'flex', alignItems: 'flex-start', marginLeft: 2}}
                                            >
                                                <FormControlLabel value="0" control={<Radio/>} label="Direct"/>
                                                <FormControlLabel value="1" control={<Radio/>} label="One stop"/>
                                                <FormControlLabel value="2" control={<Radio/>} label="Two stops"/>
                                            </RadioGroup>
                                        </FormControl>
                                    </CardContent>
                                </Card>
                            </Grid>
                        </SoftBox>
                    </Grid>
                </SoftBox>
                <SoftBox sx={{gridArea: "main"}}>
                    {
                        flights?.length
                            ? <FlightsList flights={flights}/>
                            : <p>No flights</p>
                    }
                </SoftBox>
            </Grid>
        </BasicLayout>
    );
}

export default SearchResults;