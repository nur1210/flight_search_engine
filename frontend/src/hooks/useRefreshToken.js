import useAuth from "./useAuth";
import AuthService from "../services/AuthService";

const UseRefreshToken = () => {
    const { setAuth } = useAuth();

    const refresh = async () => {
        const response = await AuthService.refresh();
        setAuth(prev => {
            console.log(JSON.stringify(prev));
            console.log(JSON.stringify(response.data.accessToken));
            return {
                ...prev,
                accessToken: response.data.accessToken
            }
            });
        return response.data.accessToken;
    };
    return refresh;
};

export default UseRefreshToken;

