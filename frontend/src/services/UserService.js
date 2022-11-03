import axios, {axiosPrivate} from "../api/axios";

const getAll = async () => {
    return await axiosPrivate.get("/users");
}

const get = async id => {
    return await axios.get(`/users/${id}`);
}

const create = async data => {
    return await axios.post('/users', data);
}

const update = async (id, data) => {
    return await axios.put(`/users/${id}`, data);
}

const remove = async id => {
    return await axios.delete(`/users/${id}`);
}

const UserService = {
    getAll,
    get,
    create,
    update,
    remove
}

export default UserService;