import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import SearchFormSideBar from "./SearchFormSideBar";
import SearchFormMain from "./SearchFormMain";
import curved6 from "assets/images/curved-images/curved14.jpg";
import {Grid} from "@mui/material";
import ChatBot from "./ChatBot";


const SearchForm = () => {
    const [airport, setAirport] = useState(null);
    const [location, setLocation] = useState(null);


    useEffect(() => {
        if (location === null) return;
        const getAirport = async () => {
            try {
                const response = await tequilaService.getAirportByCords(location.latitude, location.longitude);
                console.log(response.data.airport);
                setAirport(response.data.airport);
            } catch (e) {
                console.log(e);
            }
        };
        getAirport();

    }, [location]);


    return (
        <BasicLayout
            title={"Flight Search"}
            description={"Let the journey begin"}
            image={curved6}
        >
            <Grid
                container
                direction="column"
                justifyContent="center"
                alignItems="center"
            >

                <SoftBox mb={20}>
                    <SearchFormMain setLocation={setLocation}/>
                </SoftBox>
                <SoftBox>
                    <SearchFormSideBar airport={airport}/>
                </SoftBox>
                <ChatBot airport={airport}/>
            </Grid>
        </BasicLayout>
    )
};

export default SearchForm;