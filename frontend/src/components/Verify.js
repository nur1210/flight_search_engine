import {useEffect, useState} from "react";
import userService from "../services/UserService";

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

        handleRedirect();

    }, []);

    return (
        <div>
            {status === 200 ? <h1>Account verified successfully</h1> : <h1>Account verification failed</h1>}
        </div>
    )

}

export default Verify