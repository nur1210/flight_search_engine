import {useEffect, useState} from "react";
import userService from "../services/UserService";
import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const Verify = () => {
    const [status, setStatus] = useState(null);

    useEffect(() => {
        async function handleRedirect() {
            const code = new URLSearchParams(window.location.search).get('code');
            console.log(code);
            await userService.verify(code).then((response) => {
                console.log(response);
                setStatus(response.status);
            });
        }

        handleRedirect().then(
            setTimeout(() => {
                window.location.href = '/login';
            }, 5000)
        );

    }, []);

    return (
        <SoftBox m={30}>
            {status === 200 ?
                <SoftTypography
                    verticalAlign='middle'
                    fontWeight='bold'
                    component='h1'
                >
                    Your account has been verified. You will be redirected to the login page.
                </SoftTypography>
                :
                <SoftTypography
                    verticalAlign='middle'
                    fontWeight='bold'
                    component='h1'
                >
                    Account verification failed, your account could already been verified
                </SoftTypography>}
        </SoftBox>
    )
}

export default Verify