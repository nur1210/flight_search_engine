import React, {useEffect, useState} from 'react';
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftInput from "./SoftInput";
import useAuth from "../hooks/useAuth";
import UseAxiosPrivate from "../hooks/useAxiosPrivate";
import SoftButton from "./SoftButton";
import {useForm} from "react-hook-form";
import {Card, CardContent, Grid} from "@mui/material";
import SoftAvatar from "./SoftAvatar";
import SoftTypography from "./SoftTypography";
import DefaultProjectCard from "../examples/Cards/ProjectCards/DefaultProjectCard";
import homeDecor1 from "assets/images/home-decor-1.jpg";
import homeDecor2 from "assets/images/home-decor-2.jpg";
import homeDecor3 from "assets/images/home-decor-3.jpg";
import team1 from "assets/images/team-1.jpg";
import team2 from "assets/images/team-2.jpg";
import team3 from "assets/images/team-3.jpg";
import team4 from "assets/images/team-4.jpg";
import SoftBox from "./SoftBox";
import PlatformSettings from "../layouts/profile/components/PlatformSettings";
import curved6 from "assets/images/curved-images/curved14.jpg";
import PlaceholderCard from "../examples/Cards/PlaceholderCard";



const Profile = () => {
    const [user, setUser] = useState(null);
    const {auth} = useAuth();
    const axiosPrivate = UseAxiosPrivate();
    const {register, handleSubmit, formState: {errors}, setValue, reset, watch} = useForm();

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


    useEffect(() => {
        if (user) {
            reset({
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email,
            });
        }
    }, [user]);

    const onSubmit = async (data) => {
        console.log(data);
        try {
            let userId = auth?.userId;
            const response = await axiosPrivate.put(`/users/${userId}`, JSON.stringify(data));
            console.log(response.data);
            setUser(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <BasicLayout
            light={true}
            title="Profile"
            image={curved6}
        >
{/*            <form onSubmit={handleSubmit(onSubmit)} className='d-flex align-content-center justify-content-center '>
                <Card sx={{width: '25%'}}>
                    <CardContent sx={{marginTop: 2}}>
                        <SoftInput
                            type="text"
                            label="First Name"
                            sx={{marginBottom: 2}}
                            {...register("firstName", {required: true})}
                        />
                        <SoftInput
                            type="text"
                            sx={{marginBottom: 2}}
                            {...register("lastName", {required: true})}
                        />
                        <SoftInput
                            type="email"
                            sx={{marginBottom: 2}}
                            {...register("email", {required: true})}
                        />
                        <SoftInput
                            type="password"
                            label="Password"
                            sx={{marginBottom: 2}}
                            {...register("password",
                                {
                                    required: !!watch("newPassword")
                                })
                            }
                        />
                        <SoftInput
                            type="password"
                            {...register("newPassword",
                                {
                                    required: !!watch("password"),
                                    minLength: {
                                        value: 8,
                                        message: "Password must have at least 8 characters"
                                    }
                                })
                            }
                        />
                    </CardContent>
                    <CardContent>
                        <SoftButton type={"submit"}>Submit</SoftButton>
                    </CardContent>
                </Card>
            </form>*/}

            <SoftBox mt={5} mb={3}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={6} xl={4}>
                        <PlatformSettings />
                    </Grid>
                    <Grid item xs={12} md={6} xl={4}>
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <Card>
                        <SoftBox pt={2} px={2}>
                            <SoftTypography variant="h6" fontWeight="medium" textTransform="capitalize">
                                platform settings
                            </SoftTypography>
                        </SoftBox>
                        <SoftBox pt={1.5} pb={2} px={2} lineHeight={1.25}>
                            <SoftTypography variant="caption" fontWeight="bold" color="text" textTransform="uppercase">
                                account
                            </SoftTypography>
                                <CardContent sx={{marginTop: 2}}>
                                    <SoftInput
                                        type="text"
                                        label="First Name"
                                        sx={{marginBottom: 2}}
                                        {...register("firstName", {required: true})}
                                    />
                                    <SoftInput
                                        type="text"
                                        sx={{marginBottom: 2}}
                                        {...register("lastName", {required: true})}
                                    />
                                    <SoftInput
                                        type="email"
                                        sx={{marginBottom: 2}}
                                        {...register("email", {required: true})}
                                    />
                                    <SoftInput
                                        type="password"
                                        label="Password"
                                        sx={{marginBottom: 2}}
                                        {...register("password",
                                            {
                                                required: !!watch("newPassword")
                                            })
                                        }
                                    />
                                    <SoftInput
                                        type="password"
                                        {...register("newPassword",
                                            {
                                                required: !!watch("password"),
                                                minLength: {
                                                    value: 8,
                                                    message: "Password must have at least 8 characters"
                                                }
                                            })
                                        }
                                    />
                                </CardContent>
                                <CardContent>
                                    <SoftButton variant={"gradient"} color={"dark"} type={"submit"}>Submit</SoftButton>
                                </CardContent>
                        </SoftBox>
                            </Card>
                        </form>
                    </Grid>
                    <Grid item xs={12} xl={4}>
                    </Grid>
                </Grid>
            </SoftBox>
            <SoftBox mb={3}>
                <Card>
                    <SoftBox pt={2} px={2}>
                        <SoftBox mb={0.5}>
                            <SoftTypography variant="h6" fontWeight="medium">
                                Projects
                            </SoftTypography>
                        </SoftBox>
                        <SoftBox mb={1}>
                            <SoftTypography variant="button" fontWeight="regular" color="text">
                                Architects design houses
                            </SoftTypography>
                        </SoftBox>
                    </SoftBox>
                    <SoftBox p={2}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} md={6} xl={3}>
                                <DefaultProjectCard
                                    image={homeDecor1}
                                    label="project #2"
                                    title="modern"
                                    description="As Uber works through a huge amount of internal management turmoil."
                                    action={{
                                        type: "internal",
                                        route: "/pages/profile/profile-overview",
                                        color: "info",
                                        label: "view project",
                                    }}
                                    authors={[
                                        { image: team1, name: "Elena Morison" },
                                        { image: team2, name: "Ryan Milly" },
                                        { image: team3, name: "Nick Daniel" },
                                        { image: team4, name: "Peterson" },
                                    ]}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} xl={3}>
                                <DefaultProjectCard
                                    image={homeDecor2}
                                    label="project #1"
                                    title="scandinavian"
                                    description="Music is something that every person has his or her own specific opinion about."
                                    action={{
                                        type: "internal",
                                        route: "/pages/profile/profile-overview",
                                        color: "info",
                                        label: "view project",
                                    }}
                                    authors={[
                                        { image: team3, name: "Nick Daniel" },
                                        { image: team4, name: "Peterson" },
                                        { image: team1, name: "Elena Morison" },
                                        { image: team2, name: "Ryan Milly" },
                                    ]}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} xl={3}>
                                <DefaultProjectCard
                                    image={homeDecor3}
                                    label="project #3"
                                    title="minimalist"
                                    description="Different people have different taste, and various types of music."
                                    action={{
                                        type: "internal",
                                        route: "/pages/profile/profile-overview",
                                        color: "info",
                                        label: "view project",
                                    }}
                                    authors={[
                                        { image: team4, name: "Peterson" },
                                        { image: team3, name: "Nick Daniel" },
                                        { image: team2, name: "Ryan Milly" },
                                        { image: team1, name: "Elena Morison" },
                                    ]}
                                />
                            </Grid>
                            <Grid item xs={12} md={6} xl={3}>
                                <PlaceholderCard title={{ variant: "h5", text: "New project" }} outlined />
                            </Grid>
                        </Grid>
                    </SoftBox>
                </Card>
            </SoftBox>
        </BasicLayout>
);
};

export default Profile;
