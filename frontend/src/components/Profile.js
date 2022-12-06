import React, {useEffect, useState} from 'react';
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftInput from "./SoftInput";
import useAuth from "../hooks/useAuth";
import UseAxiosPrivate from "../hooks/useAxiosPrivate";
import SoftButton from "./SoftButton";


const getUser = async (id) => {
    return {}
}


const Profile = () => {
    const [user, setUser] = useState(null);
    const [currentPassword, setCurrentPassword] = useState(null);
    const [newPassword, setNewPassword] = useState(null);
    const {auth} = useAuth();
    const axiosPrivate = UseAxiosPrivate();


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUser = async () => {
            try {
                let userId = auth?.userId;
                const response = await axiosPrivate.get(`/users/${userId}`, {signal: controller.signal});
                console.log(response.data);
                isMounted && setUser(response.data);
            } catch (error) {
                console.log(error);
            }
        };
        getUser();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, []);


    const handleSubmit = async () => {
        try {
            let userId = auth?.userId;
            const response = await axiosPrivate.put(`/users/${userId}`, JSON.stringify(user));
            console.log(response.data);
            setUser(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <BasicLayout title="Profile">
            <form>
            <SoftInput
                type="text"
                label="FirstName"
                value={user?.firstName}
                onChange={(e) => setUser({...user, firstName: e.target.value})}
            />
            <SoftInput
                type="text"
                label="LastName"
                value={user?.lastName}
                onChange={(e) => setUser({...user, lastName: e.target.value})}
            />
            <SoftInput
                type="email"
                label="Email"
                value={user?.email}
                onChange={(e) => setUser({...user, email: e.target.value})}
            />
            <SoftInput
                type="password"
                label="currentPassword"
                value={user?.currentPassword}
                onChange={(e) => setUser({...user, currentPassword: e.target.value})}
            />
            <SoftInput
                type="password"
                label="newPassword"
                value={user?.newPassword}
                onChange={(e) => setUser({...user, newPassword: e.target.value})}
            />
                <SoftButton onClick={handleSubmit}>Submit</SoftButton>
            </form>
        </BasicLayout>
    );
};

export default Profile;
