import http from "../api/axios";

const getAllFlights = async (origin, destination, departureDate, returnDate, flightType, adults, children, infants, travelClass) => {
    return await http.get(`/tequila/flights?flyFrom=${origin}&flyTo=${destination}&dateFrom=${departureDate}&dateTo=${departureDate}&returnFrom=${returnDate}&returnTo=${returnDate}&flightType=${flightType}&adults=${(parseInt(adults) + parseInt(children) + parseInt(infants))}&selectedCabins=${travelClass}&currency=EUR&language=en&maxStopovers=0&maxSectorStopovers=0`);
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
