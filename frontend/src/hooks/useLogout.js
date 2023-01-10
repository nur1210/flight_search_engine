import useAuth from "./useAuth";
import authService from "../services/AuthService";
import useClient from "./useStompClient";

const useLogout = () => {
    const { setAuth } = useAuth();
    const { client, privateClient } = useClient();

    const logout = async () => {
        setAuth({});
        try {
            await authService.logout();
            client.unsubscribe('/topic/notifications');
            privateClient.unsubscribe(`/user/topic/specific-notifications`);
        } catch (err) {
            console.error(err);
        }
    }

    return logout;
}

export default useLogout