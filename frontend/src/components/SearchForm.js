import {useState} from "react";
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {z} from 'zod';
import {createSearchParams, Link, useNavigate} from "react-router-dom";
import Container from "react-bootstrap/Container";



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
        const [flights, setFlights] = useState([]);


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


    const handleSubmit = (e) => {
        e.preventDefault();
        z.date().min(new Date(Date.now()), {message: "Departure date must be in the future"}).safeParse(departureDate);
        z.date().min(new Date(departureDate), {message: "Return date must be after departure date"}).safeParse(returnDate);
        retrieveFlights();
    };

    const retrieveFlights = () => {
        tequilaService.getAllFlights(origin, destination, departureDate, returnDate, flightType, adults, children, infants, travelClass)
            .then(response => {
                setFlights(response.data.flights);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    return (
        <Container>
        <form onSubmit={handleSubmit}>
            <div className="container-sm">
                <LocationsCard setOrigin={setOrigin} setDestination={setDestination}/>
                <div className="row">
                    <DatesCard setFlightType={setFlightType} setDepartureDate={setDepartureDate} setReturnDate={setReturnDate}/>
                    <DetailsCard travelClass={travelClass} setTravelClass={setTravelClass} setAdults={setAdults} adults={adults}
                                 setChildren={setChildren} children={children} setInfants={setInfants} indants={infants}/>
                </div>
                <button id="search-button" onClick={post} className="w-100 btn btn-dark" type="submit">Search</button>
            </div>
        </form>
    </Container>
    )   };

export default SearchForm;