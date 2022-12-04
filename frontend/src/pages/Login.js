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

const Login = () => {
    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const userRef = useRef();
    const errRef = useRef();

    const [email, setEmail] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg('');
    }, [email, pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await AuthService.login(email, pwd);
            console.log(response);
            const accessToken = response.data.accessToken;
            const roles = response.data.roles;
            const loggedIn = true;
            console.log(accessToken);
            setAuth({ email, roles, accessToken, loggedIn });
            setEmail('');
            setPwd('');
            navigate(from, { replace: true });
        } catch (err) {
            console.log(err);
            if (err.response.status === 400) {
                setErrMsg('Incorrect Email or Password');
            } else if (err.response.status === 401) {
                setErrMsg('Unauthorized');
            } else {
                setErrMsg('Login Failed');
            }
            errRef.current.focus();
        }
    }

    return (

        <CoverLayout
            title="Welcome back"
            description="Enter your email and password to sign in"
            image={curved9}
        >
            <SoftTypography ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</SoftTypography>
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
                <SoftButton variant="gradient" color="info" fullWidth type={handleSubmit}>
                    Sign In
                </SoftButton>
                </SoftBox>
            </SoftBox>
            <SoftBox mt={3} textAlign="center">
                <SoftTypography variant="button" color="text" fontWeight="regular">
                    Don&apos;t have an account?{" "}
                    <SoftTypography
                        component={Link}
                        to="/authentication/sign-up"
                        variant="button"
                        color="info"
                        fontWeight="medium"
                        textGradient
                    >
                        Sign up
                    </SoftTypography>
                </SoftTypography>
            </SoftBox>
        </CoverLayout>

    )
}

export default Login