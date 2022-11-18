import {useState} from "react";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {createSearchParams, useNavigate} from "react-router-dom";
import Container from "react-bootstrap/Container";
import {useForm} from "react-hook-form";



const SearchForm = () => {
    const [origin, setOrigin] = useState('');
    const [destination, setDestination] = useState('');
    const [flightType, setFlightType] = useState('');
    const [departureDate, setDepartureDate] = useState('');
    const [returnDate, setReturnDate] = useState('');
    const [travelClass, setTravelClass] = useState('');
    const [adults, setAdults] = useState(1);
    const [children, setChildren] = useState(0);
    const [infants, setInfants] = useState(0);
    const { register, handleSubmit, errors } = useForm();


    const navigate = useNavigate();
    const params = {
        origin: origin,
        destination: destination,
        flightType: flightType,
        departureDate: departureDate,
        returnDate: returnDate,
        travelClass: travelClass,
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
        alert(JSON.stringify(data));
        post();
    };

    return (
        <Container>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="container-sm">
                    <LocationsCard setOrigin={setOrigin} setDestination={setDestination} register={register}/>
                    <div className="row">
                        <DatesCard setFlightType={setFlightType} setDepartureDate={setDepartureDate} setReturnDate={setReturnDate} register={register}/>
                        <DetailsCard travelClass={travelClass} setTravelClass={setTravelClass} setAdults={setAdults} adults={adults}
                                     setChildren={setChildren} children={children} setInfants={setInfants} infants={infants} register={register}/>
                    </div>
                    <button id="search-button" className="w-100 btn btn-dark" type="submit">Search</button>
                </div>
            </form>
        </Container>
    )   };

export default SearchForm;