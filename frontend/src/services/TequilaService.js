import http from "../api/axios";

const getAllFlights = async (flyFrom, flyTo, dateFrom, returnFrom, flightType, adults, children, infants, selectedCabins, stops) => {
    return await http.get(`/tequila/flights?flyFrom=${flyFrom}&flyTo=${flyTo}&dateFrom=${dateFrom}&dateTo=${dateFrom}&returnFrom=${returnFrom}&returnTo=${returnFrom}&flightType=${flightType}&adults=${(parseInt(adults) + parseInt(children) + parseInt(infants))}&selectedCabins=${selectedCabins}&currency=EUR&language=en&maxStopovers=${parseInt(stops) * 2}&maxSectorStopovers=${parseInt(stops)}&limit=20`);
}

const getTopThreeCheapestFlightsFromUserLocation = async (origin, dateFrom, dateTo, returnFrom, returnTo, minNightsInDestination, maxNightsInDestination, flightType, adults, travelClass) => {
    return await http.get(`/tequila/flights/cheapest?flyFrom=${origin}&dateFrom=${dateFrom}&dateTo=${dateTo}&returnFrom=${returnFrom}&returnTo=${returnTo}&minNightsInDestination=${minNightsInDestination}&maxNightsInDestination=${maxNightsInDestination}&flightType=${flightType}&returnFromDifferentCity=false&returnToDifferentCity=false&onePerDestination=1&adults=${adults}&selectedCabins=${travelClass}&onlyWorkingDays=false&onlyWeekends=false&currency=EUR&language=en&maxStopovers=0&maxSectorStopovers=0&limit=3`);
}

const getAdvanceSearchFlights = async (flyFrom, flyTo, dateFrom, dateTo, minNightsInDestination, maxNightsInDestination, flightType, adults, travelClass, maxStops) => {
    return await http.get(`/tequila/flights/advance?flyFrom=${flyFrom}&flyTo=${flyTo}&dateFrom=${dateFrom}&dateTo=${dateTo}&returnFrom=${dateFrom}&returnTo=${dateTo}&minNightsInDestination=${minNightsInDestination}&maxNightsInDestination=${maxNightsInDestination}&flightType=${flightType}&returnFromDifferentCity=false&returnToDifferentCity=false&onePerDestination=0&adults=${adults}&selectedCabins=${travelClass}&onlyWorkingDays=false&onlyWeekends=false&currency=EUR&language=en&maxStopovers=${parseInt(maxStops) * 2}&maxSectorStopovers=${parseInt(maxStops)}&limit=3`);
}

const getAirportByCity = async (city) => {
    return await http.get(`/tequila/locations/city?city=${city}`);
}

const getAirportByCords = async (lat, lon) => {
    return await http.get(`/tequila/locations/cords?lat=${lat}&lng=${lon}`);
}

const getTopTenDestinations = async (city) => {
    return await http.get(`/tequila/locations/top-destinations?city=${city}`);
}

const TequilaService = {
    getAllFlights,
    getTopThreeCheapestFlightsFromUserLocation,
    getAdvanceSearchFlights,
    getAirportByCity,
    getAirportByCords,
    getTopTenDestinations
}

export default TequilaService;
