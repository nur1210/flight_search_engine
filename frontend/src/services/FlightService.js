import http from "../http-common";

const getAll = () => {
    return http.get("/flights");
}

const get = id => {
    return http.get(`/flights/${id}`);
}

const create = data => {
    return http.post("/flights", data);
}

const update = (id, data) => {
    return http.put(`/flights/${id}`, data);
}

const remove = id => {
    return http.delete(`/flights/${id}`);
}

const FlightService = {
    getAll,
    get,
    create,
    update,
    remove
}

export default FlightService;