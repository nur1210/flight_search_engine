import {useState} from "react";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {createSearchParams, useNavigate} from "react-router-dom";
import {useForm, useFormState} from "react-hook-form";
import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import Grid from "@mui/material/Grid";
import SoftButton from "./SoftButton";


const SearchForm = () => {
    const [flightType, setFlightType] = useState('');
    const [departureDate, setDepartureDate] = useState('');
    const [returnDate, setReturnDate] = useState('');
    const [travelClass, setTravelClass] = useState('');
    const [adults, setAdults] = useState(1);
    const [children, setChildren] = useState(0);
    const [infants, setInfants] = useState(0);
    const {register, handleSubmit, formState: {errors}, getValues, setValue} = useForm();


    const navigate = useNavigate();
    const params = {
        origin: getValues('Origin'),
        destination: getValues('Destination'),
        flightType: getValues('flightType'),
        departureDate: getValues('Departure date'),
        returnDate: getValues('Arrival date'),
        travelClass: getValues('travelClass'),
        adults: adults,
        children: children,
        infants: infants
    }

    const post = () => navigate({
        pathname: '/search-results',
        search: `?${createSearchParams(params)}`
    });


    const onSubmit = (data) => {
        console.log(errors);
        console.log(JSON.stringify(getValues))
        console.log(JSON.stringify(data));
        console.log(params);
        post();
    };

    return (
        <BasicLayout
            title={"Flight Search"}
            description={"Let the journey begin"}
        >
            <Grid>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <SoftBox>
                        <SoftBox mt={4} mb={2}>
                            <LocationsCard register={register} value={setValue} errors={errors}/>
                        </SoftBox>
                        <SoftBox className="row">
                            <DatesCard setFlightType={setFlightType} setDepartureDate={setDepartureDate}
                                       setReturnDate={setReturnDate} register={register}/>
                            <DetailsCard travelClass={travelClass} setTravelClass={setTravelClass} setAdults={setAdults}
                                         adults={adults}
                                         setChildren={setChildren} children={children} setInfants={setInfants}
                                         infants={infants} register={register}/>
                        </SoftBox>
                        <SoftButton id="search-button" className="w-100" color={"dark"}
                                    type="submit">Search</SoftButton>
                    </SoftBox>
                </form>
            </Grid>
        </BasicLayout>
    )
};

export default SearchForm;