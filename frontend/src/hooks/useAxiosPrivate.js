import useRefreshToken from "./useRefreshToken";
import useAuth from "./useAuth";
import {useEffect} from "react";
import {axiosPrivate} from "../api/axios";

const useAxiosPrivate = () => {
    const refresh = useRefreshToken();
    const {auth} = useAuth();

    useEffect(() => {

        const requestInterceptor = axiosPrivate.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']){
                    config.headers['Authorization'] = `Bearer ${auth?.accessToken}`;
                }
                return config;
            }, error => Promise.reject(error)
        );

        const responseInterceptor = axiosPrivate.interceptors.response.use(
            response => response,
            async (error) => {
                const prevRequest = error?.config;
                if (error?.response.status === 403 && !prevRequest.retry) {
                    prevRequest.retry = true;
                    const newAccessToken = await refresh();
                    prevRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    console.log('newAccessToken', newAccessToken);
                    return axiosPrivate(prevRequest);
                }
                return Promise.reject(error);
            }
        );
        return () => {
            axiosPrivate.interceptors.request.eject(requestInterceptor);
            axiosPrivate.interceptors.response.eject(responseInterceptor);
        };
    }, [auth, refresh]);

    return axiosPrivate;
}

export default useAxiosPrivate;