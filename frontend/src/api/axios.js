import axios from "axios";
const BASE_URL = "http://localhost:8080";

export default axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-type": "application/json",
        "Access-Control-Allow-Origin":  "http://localhost:3000",
        'Access-Control-Allow-Methods':'GET, POST, OPTIONS, PUT, PATCH, DELETE',
        'Access-Control-Allow-Headers': 'X-Requested-With,content-type',
        'Access-Control-Allow-Credentials': true
    }
});

export const axiosPrivate = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-type": "application/json",
        "Access-Control-Allow-Origin":  "http://localhost:3000",
        'Access-Control-Allow-Methods':'GET, POST, OPTIONS, PUT, PATCH, DELETE',
        'Access-Control-Allow-Headers': 'X-Requested-With,content-type',
        'Access-Control-Allow-Credentials': true
    },
    withCredentials: true
});

