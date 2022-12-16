import http from "../api/axios";

const getAllFlights = async (flyFrom, flyTo, dateFrom, returnFrom, flightType, adults, children, infants, selectedCabins) => {
    return await http.get(`/tequila/flights?flyFrom=${flyFrom}&flyTo=${flyTo}&dateFrom=${dateFrom}&dateTo=${dateFrom}&returnFrom=${returnFrom}&returnTo=${returnFrom}&flightType=${flightType}&adults=${(parseInt(adults) + parseInt(children) + parseInt(infants))}&selectedCabins=${selectedCabins}&currency=EUR&language=en&maxStopovers=0&maxSectorStopovers=0`);
}

const getTopThreeCheapestFlightsFromUserLocation = async (origin, dateFrom, dateTo, returnFrom, returnTo, minNightsInDestination, maxNightsInDestination, flightType, adults, travelClass) => {
    return await http.get(`/tequila/flights/cheapest?flyFrom=${origin}&dateFrom=${dateFrom}&dateTo=${dateTo}&returnFrom=${returnFrom}&returnTo=${returnTo}&minNightsInDestination=${minNightsInDestination}&maxNightsInDestination=${maxNightsInDestination}&flightType=${flightType}&returnFromDifferentCity=false&returnToDifferentCity=false&onePerDestination=1&adults=${adults}&selectedCabins=${travelClass}&onlyWorkingDays=false&onlyWeekends=false&currency=EUR&language=en&maxStopovers=0&maxSectorStopovers=0&limit=3`);
}

const getAirportByCity = async (city) => {
    return await http.get(`/tequila/locations/city?city=${city}`);
}

const getAirportByCords = async (lat, lon) => {
    return await http.get(`/tequila/locations/cords?lat=${lat}&lng=${lon}`);
}

const TequilaService = {
    getAllFlights,
    getTopThreeCheapestFlightsFromUserLocation,
    getAirportByCity,
    getAirportByCords
}

export default TequilaService;
