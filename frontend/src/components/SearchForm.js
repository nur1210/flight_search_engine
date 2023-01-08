import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import SearchFormSideBar from "./SearchFormSideBar";
import SearchFormMain from "./SearchFormMain";
import curved6 from "assets/images/curved-images/curved14.jpg";
import {Grid} from "@mui/material";
import ChatBot from "../ChatBot";


const SearchForm = () => {
    const [airport, setAirport] = useState(null);
    const [location, setLocation] = useState(null);


    useEffect(() => {
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
            {/*        <SoftBox
            sx={{
                display: 'grid',
                gap: 1,
                gridTemplateColumns: 'repeat(8, 1fr)',
                gridTemplateRows: 'auto',
                gridTemplateAreas: `"header header header header header header herder"
                                        "main main main main main"
                                        "sidebar sidebar sidebar sidebar sidebar"
                                        "footer footer footer footer footer footer footer"`,
            }}
        >*/}

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
            <ChatBot/>
            </Grid>
        </BasicLayout>
    )
};

export default SearchForm;