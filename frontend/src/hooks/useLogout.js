import useAuth from "./useAuth";
import authService from "../services/AuthService";

const useLogout = () => {
    const { setAuth } = useAuth();

    const logout = async () => {
        setAuth({});
        try {
            await authService.logout();
        } catch (err) {
            console.error(err);
        }
    }

    return logout;
}

export default useLogout