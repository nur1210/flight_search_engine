import http from "../api/axios";

const getAllFlights = async (origin, destination, departureDate, returnDate, flightType, adults, children, infants, travelClass) => {
    return await http.get(`/tequila/flights?fly_from=${origin}&fly_to=${destination}&date_from=${departureDate}&date_to=${departureDate}&return_from=${returnDate}&return_to=${returnDate}&flight_type=${flightType}&adults=${(parseInt(adults) + parseInt(children) + parseInt(infants))}&selected_cabins=${travelClass}&curr=EUR&locale=en&max_stopovers=0&max_sector_stopovers=0`);
}

const getAirportByCity = async (city) => {
    return await http.get(`/tequila/locations/city?city=${city}`);
}

const getAirportByCords = async (lat, lon) => {
    return await http.get(`/tequila/locations/cords?lat=${lat}&lng=${lon}`);
}

const TequilaService = {
    getAllFlights,
    getAirportByCity,
    getAirportByCords
}

export default TequilaService;
