import {useState} from "react";
import FlightService from "../services/FlightService";

const AddFlight = () => {
    const [flight, setFlight] = useState({
        routes: [],
        price: 0.0,
        availableSeats: 0,
    });

    const {routes, price, availableSeats} = flight;

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFlight({...flight, [name]: value});
    };

    const saveFlight = () => {
        let data = {
            routes: flight.routes,
            price: flight.price,
            availableSeats: flight.availableSeats,
        };
FlightService.create(data)
            .then((response) => {
                setFlight({
                    routes: response.data.routes,
                    price: response.data.price,
                    availableSeats: response.data.availableSeats
                });
                console.log(response.data);
            })
    }
}

export default AddFlight;

