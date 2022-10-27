import React from 'react';
import useAuth from "./useAuth";

const UseRefreshToken = () => {
    const { setAuth } = useAuth();

    const refresh = async () => {
        const response = await fetch('http://localhost:3001/refresh', {
            withCredentials: true
        });
        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(JSON.stringify(response.data.access_token));
            return {
                ...prev,
                access_token: response.data.access_token
            }
            });
        return response.data.access_token;
    };
    return refresh;
};

export default UseRefreshToken;

