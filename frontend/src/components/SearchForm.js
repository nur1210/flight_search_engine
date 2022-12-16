import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import SearchFormSideBar from "./SearchFormSideBar";
import SearchFormMain from "./SearchFormMain";
import {Tab, Tabs} from "react-bootstrap";

const SearchForm = () => {
    const [airport, setAirport] = useState(null);
    const [location, setLocation] = useState(null);


    useEffect (() => {
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


    return (<BasicLayout
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
                    <SoftBox sx={{gridArea: "main"}}>

                        <Tabs defaultActiveKey={"main"}>
                            <Tab eventKey={"main"} title={"Main"}>
                                <SearchFormMain setLocation={setLocation}/>
                            </Tab>
                            <Tab eventKey={"advance"} title={"Advance"}>
                                <SoftBox>hekki</SoftBox>
                            </Tab>
                        </Tabs>
                    </SoftBox>
                <SearchFormSideBar airport={airport}/>
            </SoftBox>
        </BasicLayout>)
};

export default SearchForm;