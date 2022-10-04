import http from "../http-common";

const getAll = async (origin, destination, departureDate, returnDate, flightType, adults, children, infants, travelClass) => {
    return await http.get(`/tequila?fly_from=${origin}&fly_to=${destination}&date_from=${departureDate}&date_to=${departureDate}&return_from=${returnDate}&return_to=${returnDate}&flight_type=${flightType}&adults=${(parseInt(adults) + parseInt(children) + parseInt(infants))}&selected_cabins=${travelClass}&curr=EUR&locale=en&max_stopovers=0&max_sector_stopovers=0`);
}

const TequilaService = {
    getAll
}

export default TequilaService;
