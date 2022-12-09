import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import SearchFormSideBar from "./SearchFormSideBar";
import SearchFormMain from "./SearchFormMain";

const SearchForm = () => {
    const [airport, setAirport] = useState(null);
    const [location, setLocation] = useState(null);


    useEffect(() => {
        try {
            console.log(location)
            tequilaService.getAirportByCords(location.latitude, location.longitude).then(response => {
                console.log(response.data.airport)
                setAirport(response.data.airport);
            });
        } catch (e) {
            console.log(e);
        }

    }, [location]);






    return (
        <BasicLayout
            title={"Flight Search"}
            description={"Let the journey begin"}
        >
            <SoftBox
                sx={{
                    display: 'grid',
                    gap: 1,
                    gridTemplateColumns: 'repeat(8, 1fr)',
                    gridTemplateRows: 'auto',
                    gridTemplateAreas: `"header header header header header header herder"
                                        "sidebar sidebar main main main main main"
                                        "footer footer footer footer footer footer footer"`,
                }}
            >
                <SearchFormSideBar airport={airport}/>
                <SearchFormMain setLocation={setLocation}/>
            </SoftBox>
        </BasicLayout>
    )
};

export default SearchForm;