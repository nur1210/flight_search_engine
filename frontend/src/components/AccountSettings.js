import {Card, CardContent} from "@mui/material";
import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";
import SoftInput from "./SoftInput";
import SoftButton from "./SoftButton";
import React, {useEffect} from "react";
import {useForm} from "react-hook-form";
import UseAxiosPrivate from "../hooks/useAxiosPrivate";
import {toast, ToastContainer} from "react-toastify";

const AccountSettings = ({user, setUser, auth}) => {
    const axiosPrivate = UseAxiosPrivate();
    const {register, handleSubmit, formState: {errors}, setValue, reset, watch} = useForm();

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

    const onError = async (errors) => {
        console.log(errors);
        toast.error(errors[Object.keys(errors)[0]].message);
    };


    return(
        <form onSubmit={handleSubmit(onSubmit, onError)}>
            <Card>
                <SoftBox pt={2} px={2}>
                    <SoftTypography variant="h6" fontWeight="medium" textTransform="capitalize">
                        Account settings
                    </SoftTypography>
                </SoftBox>
                <SoftBox pt={1.5} pb={2} px={2} lineHeight={1.25}>
                    <SoftTypography variant="caption" fontWeight="bold" color="text"
                                    textTransform="uppercase">
                        Personal information
                    </SoftTypography>
                    <CardContent sx={{marginTop: 2}}>
                        <SoftInput
                            type="text"
                            placeholder="First Name"
                            sx={{marginBottom: 2}}
                            {...register("firstName", {required: "First name is required"})}
                        />
                        <SoftInput
                            type="text"
                            placeholder="Last Name"
                            sx={{marginBottom: 2}}
                            {...register("lastName", {required: "Last name is required"})}
                        />
                        <SoftInput
                            type="email"
                            placeholder="Email"
                            sx={{marginBottom: 2}}
                            {...register("email", {required: "Email is required"})}
                        />
                        <SoftInput
                            type="password"
                            placeholder="Current Password"
                            sx={{marginBottom: 2}}
                            {...register("password",
                                {
                                    required: !!watch("newPassword")
                                })
                            }
                        />
                        <SoftInput
                            type="password"
                            placeholder="New Password"
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
                        <SoftButton variant={"gradient"} color={"dark"}
                                    type={"submit"}>Submit</SoftButton>
                    </CardContent>
                </SoftBox>
            </Card>
            <ToastContainer position="bottom-right"/>
        </form>
    )

}

export default AccountSettings