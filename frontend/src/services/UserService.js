import axios from "../api/axios";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const getAll = () => {
    return useAxiosPrivate.get("/users");
}

const get = id => {
    return axios.get(`/users/${id}`);
}

const create = data => {
    return axios.post('/users', data);
}

const update = (id, data) => {
    return axios.put(`/users/${id}`, data);
}

const remove = id => {
    return axios.delete(`/users/${id}`);
}

const UserService = {
    getAll,
    get,
    create,
    update,
    remove
}

export default UserService;