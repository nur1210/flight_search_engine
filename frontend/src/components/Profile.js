import React, {useEffect, useState} from 'react';
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SoftInput from "./SoftInput";
import useAuth from "../hooks/useAuth";
import UseAxiosPrivate from "../hooks/useAxiosPrivate";
import SoftButton from "./SoftButton";
import {useForm} from "react-hook-form";

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
        <BasicLayout title="Profile">
            <form onSubmit={handleSubmit(onSubmit)}>
                <SoftInput
                    type="text"
                    {...register("firstName", {required: true})}
                />
                <SoftInput
                    type="text"
                    {...register("lastName", {required: true})}
                />
                <SoftInput
                    type="email"
                    {...register("email", {required: true})}
                />
                <SoftInput
                    type="password"
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
                <SoftButton type={"submit"}>Submit</SoftButton>
            </form>
        </BasicLayout>
    );
};

export default Profile;
