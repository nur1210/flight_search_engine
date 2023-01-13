import { useRef, useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';

import AuthService from "../services/AuthService";
import useAuth from "../hooks/useAuth";
import curved9 from "../assets/images/curved-images/curved-6.jpg";
import CoverLayout from "../layouts/authentication/components/CoverLayout";
import SoftBox from "../components/SoftBox";
import SoftTypography from "../components/SoftTypography";
import SoftInput from "../components/SoftInput";
import SoftButton from "../components/SoftButton";
import {toast, ToastContainer} from "react-toastify";

const Login = () => {
    const { setAuth, persist, setPersist } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const userRef = useRef();
    const errRef = useRef();

    const [email, setEmail] = useState('');
    const [pwd, setPwd] = useState('');

    useEffect(() => {
        userRef.current.focus();
    }, [])


    useEffect(() => {
        localStorage.setItem("persist", persist);
    }, [persist])

    const notify = (message) => toast.error(message);


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await AuthService.login(email, pwd);
            console.log(response);
            const accessToken = response.data.accessToken;
            const userId = response.data.userId;
            const roles = response.data.roles;
            const loggedIn = true;
            console.log(accessToken);
            setAuth({ email, userId, roles, accessToken, loggedIn });
            setEmail('');
            setPwd('');
            navigate(from, { replace: true });
        } catch (err) {
            console.log(err);
            if (err.response.status === 400) {
                notify('Incorrect Email or Password');
            } else if (err.response.status === 401) {
                notify('Not Verified', 'Please verify your email');
            } else {
                notify('Login Failed');
            }
            errRef.current.focus();
        }
    }

    const togglePersist = () => {
        setPersist(prev => !prev);
    }

    return (

        <CoverLayout
            title="Welcome back"
            description="Enter your email and password to sign in"
            image={curved9}
            color={"dark"}
        >
            <SoftBox component={"form"} role={"form"} onSubmit={handleSubmit}>
                <SoftBox mt={2} mb={1} ml={0.5}>
                    <SoftTypography component="label" variant="caption" fontWeight="bold">
                        Email
                    </SoftTypography>
                </SoftBox>
                <SoftInput
                    type="text"
                    id="email"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => setEmail(e.target.value)}
                    value={email}
                    required
                />

                <SoftBox mt={2} mb={1} ml={0.5}>
                    <SoftTypography component="label" variant="caption" fontWeight="bold">
                        Password
                    </SoftTypography>
                </SoftBox>
                <SoftInput
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    value={pwd}
                    required
                />
                <SoftBox mt={4} mb={1}>
                <SoftButton variant="gradient" color="dark" fullWidth type={handleSubmit}>
                    Sign In
                </SoftButton>
                </SoftBox>
            </SoftBox>
            <SoftBox mt={3} textAlign="center">
                <SoftTypography variant="button" color="dark" fontWeight="regular">
                    Don&apos;t have an account?{" "}
                    <SoftTypography
                        component={Link}
                        to="/authentication/sign-up"
                        variant="button"
                        color="dark"
                        fontWeight="medium"
                        textGradient
                    >
                        Sign up
                    </SoftTypography>
                </SoftTypography>
            </SoftBox>
            <ToastContainer position="bottom-right"/>
        </CoverLayout>
    )
}

export default Login