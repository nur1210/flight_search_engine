import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {createSearchParams, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftButton from "./SoftButton";
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import DefaultInfoCard from "../examples/Cards/InfoCards/DefaultInfoCard";

const SearchForm = () => {
    const [flights, setFlights] = useState([]);
    const [location, setLocation] = useState(null);
    const navigate = useNavigate();
    const {register, handleSubmit, formState, formState: {errors}, getValues, setValue, reset} = useForm({
        defaultValues: {
            Origin: '',
            Destination: '',
            flightType: '',
            Departure: '',
            Return: '',
            travelClass: '',
            Adults: 1,
            Children: 0,
            Infants: 0
        }
    });

    useEffect(() => {
        const month = new Date().getMonth() + 1;
        const day = new Date().getDate() + 1;
        const firstDate = `${new Date().getFullYear()}-${month < 10 ? `0${month}` : `${month}`}-${day < 10 ? `0${day}` : `${day}`}`;
        const lastDate = new Date().getFullYear() + '-' + month + '-' + new Date(new Date().getFullYear(), month, 0).getDate();
        console.log(lastDate);
        console.log(location)
        try {
            const response = tequilaService.getTopThreeCheapestFlightsFromUserLocation(
                location.iata,
                firstDate,
                lastDate,
                firstDate,
                lastDate,
                1,
                7,
                'round',
                1,
                'M').then(response => {
                console.log(response.data.cheapestFlights);
                setFlights(response.data.cheapestFlights);
            });
        } catch (e) {
            console.log(e);
        }
    }, [location]);


    const post = (data) => navigate({
        pathname: '/search-results',
        search: `?${createSearchParams(data)}`
    });


    const onSubmit = (data) => {
        console.log(data);
        console.log(JSON.stringify(data));
        post(data);
    };


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
                <SoftBox sx={{gridArea: 'sidebar'}}>
                {location ?
                    <>
                        <h2>Cheapest flights from {location.city}</h2>
                        {flights.map((flight) => (
                            <SoftBox mb={2} mr={1}>
                                <DefaultInfoCard
                                    icon={"paid"}
                                    color={"secondary"}
                                    title={`Fly to: ${flight.route[0].arrivalAirport.city}, ${flight.route[0].arrivalAirport.country}`}
                                    description={`${(flight.route[0].localDepartureTime).split('T')[0].replaceAll('-', "/")} - ${(flight.route[1].localArrivalTime).split('T')[0].replaceAll('-', "/")}`}
                                    value={`Price: ${flight.price}€`}>
                                </DefaultInfoCard>
                            </SoftBox>
                        ))}
                    </>
                    :
                    null
                }
                </SoftBox>
                <SoftBox sx={{gridArea: 'main'}}>
                    <form onSubmit={handleSubmit(onSubmit)} className={"w-100"}>
                        <SoftBox>
                            <SoftBox mt={4} mb={2}>
                                <LocationsCard register={register} setLocation={setLocation} setValue={setValue} errors={errors}/>
                            </SoftBox>
                            <SoftBox className="row">
                                <DatesCard register={register}/>
                                <DetailsCard register={register}/>
                            </SoftBox>
                            <SoftButton id="search-button" className="w-100" color={"dark"}
                                        type="submit">Search</SoftButton>
                        </SoftBox>
                    </form>
                </SoftBox>
            </SoftBox>
        </BasicLayout>
    )
};

export default SearchForm;