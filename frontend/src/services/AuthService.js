import http from "../api/axios";

const login = async (email, password) => {
    const response = await http.post("/auth/login",
        JSON.stringify({email, password}),
        {
            headers: {
                "Content-Type": "application/json",
                "withCredentials": true
            },
        });
    return response.data;
}

const AuthService = {
    login,
};

export default AuthService;