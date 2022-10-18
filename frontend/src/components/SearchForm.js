import {useState} from "react";
import tequilaService from "../services/TequilaService";
import FlightsList from "./FlightsList";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {z} from 'zod';


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
        <div>
        <form onSubmit={handleSubmit}>
            <div className="container-sm">
                <LocationsCard setOrigin={setOrigin} setDestination={setDestination}/>
                <div className="row">
                    <DatesCard setFlightType={setFlightType} setDepartureDate={setDepartureDate} setReturnDate={setReturnDate}/>
                    <DetailsCard travelClass={travelClass} setTravelClass={setTravelClass} setAdults={setAdults} adults={adults} setChildren={setChildren} children={children} setInfants={setInfants} indants={infants}/>
                </div>
                <button id="search-button" className="w-100 btn btn-dark" type="submit">Search</button>
            </div>
        </form>
    <div>
        <FlightsList flights={flights}/>
    </div>
    </div>
    )};

export default SearchForm;