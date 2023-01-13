import React, {useEffect, useState} from 'react';
import BasicLayout from "./BasicLayout";
import useAuth from "../hooks/useAuth";
import UseAxiosPrivate from "../hooks/useAxiosPrivate";
import {Grid} from "@mui/material";
import SoftBox from "./SoftBox";
import curved6 from "assets/images/curved-images/curved14.jpg";
import AccountSettings from "./AccountSettings";


const Profile = () => {
    const [user, setUser] = useState(null);
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



    return (
        <BasicLayout
            light={true}
            title="Profile"
            image={curved6}
        >
            <SoftBox mt={5} mb={3}>
                <Grid container spacing={3} justifyContent="center">
                    <Grid item xs={12} md={6} xl={4}>
                      <AccountSettings user={user} setUser={setUser} auth={auth}/>
                    </Grid>
                </Grid>
            </SoftBox>
        </BasicLayout>
    );
};

export default Profile;
