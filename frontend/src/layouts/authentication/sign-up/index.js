import {Link, useNavigate} from "react-router-dom";
import Card from "@mui/material/Card";
import SoftBox from "components/SoftBox";
import SoftTypography from "components/SoftTypography";
import SoftInput from "components/SoftInput";
import SoftButton from "components/SoftButton";
import BasicLayout from "layouts/authentication/components/BasicLayout";
import {useForm} from "react-hook-form";
import userService from "../../../services/UserService";
import {toast, ToastContainer} from "react-toastify";


function SignUp() {
    const {register, handleSubmit, watch} = useForm();
    const navigate = useNavigate();
    const error = (message) => toast.error(message);
    const success = (message) => toast.success(message);

    const onSubmit = async (data) => {
        console.log(data);
        try {
            const response = await userService.create(JSON.stringify(data));
            console.log(response.data);
            success("User created successfully");
            setTimeout(4000)
            navigate('/login');
        } catch (error) {
            console.log(error);
        }
    };

    const onError = async (errors) => {
        console.log(errors);
        error(errors[Object.keys(errors)[0]].message);
    };

    return (
        <BasicLayout
            title="Welcome!">
            <SoftBox
                mt={2}
                display="flex"
                flexDirection="column"
                justifyContent="center"
                alignItems="center"
            >
                <Card sx={{width: "50%"}}>
                    <SoftBox pt={2} pb={3} px={3}>
                        <SoftBox component="form" role="form" onSubmit={handleSubmit(onSubmit, onError)}>
                            <SoftBox mb={2}>
                                <SoftInput placeholder="First name"
                                           {...register("firstName", {required: "First name is required"})}
                                />
                            </SoftBox>
                            <SoftBox mb={2}>
                                <SoftInput placeholder="Last name"
                                           {...register("lastName", {required: "Last name is required"})}
                                />
                            </SoftBox>
                            <SoftBox mb={2}>
                                <SoftInput type="email" placeholder="Email"
                                           {...register("email", {required: "Email is required"})}
                                />
                            </SoftBox>
                            <SoftBox mb={2}>
                                <SoftInput type="password" placeholder="Password"
                                           {...register("password", {
                                               required: "Password has to be 8-20 characters",
                                               minLength: 8,
                                               maxLength: 20
                                           })}
                                />
                            </SoftBox>
                            <SoftBox mb={2}>
                                <SoftInput type="password" placeholder="Repeat password"
                                           {...register("repeatPassword", {
                                               required: true,
                                               validate: (value) => value === watch('password') || "Passwords don't match"
                                           })}
                                />
                            </SoftBox>
                            <SoftBox mt={4} mb={1}>
                                <SoftButton variant="gradient" color="dark" fullWidth type="submit">
                                    sign up
                                </SoftButton>
                            </SoftBox>
                            <SoftBox mt={3} textAlign="center">
                                <SoftTypography variant="button" color="text" fontWeight="regular">
                                    Already have an account?&nbsp;
                                    <SoftTypography
                                        component={Link}
                                        to="/login"
                                        variant="button"
                                        color="dark"
                                        fontWeight="bold"
                                        textGradient
                                    >
                                        Sign in
                                    </SoftTypography>
                                </SoftTypography>
                            </SoftBox>
                        </SoftBox>
                    </SoftBox>
                </Card>
            </SoftBox>
            <ToastContainer position="bottom-right"/>
        </BasicLayout>
    );
}

export default SignUp;
