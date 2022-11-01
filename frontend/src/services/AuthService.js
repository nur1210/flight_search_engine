import axios from "../api/axios";

const login = async (email, password) => {
    return await axios.post("/auth/login",
        JSON.stringify({email, password}),
        {
            headers: {
                "Content-Type": "application/json",
                "withCredentials": true
            },
        });
}

const refresh = async () => {
    return await axios.get("/auth/refresh",
        {
            withCredentials: true
        });
}

const AuthService = {
    login,
    refresh
};

export default AuthService;