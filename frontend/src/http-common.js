import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-type": "application/json",
        "Access-Control-Allow-Origin":  "http://localhost:3000",
        'Access-Control-Allow-Methods':'GET, POST, OPTIONS, PUT, PATCH, DELETE',
        'Access-Control-Allow-Headers': 'X-Requested-With,content-type',
        'Access-Control-Allow-Credentials': true
    }
});

