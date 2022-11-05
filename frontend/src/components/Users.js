import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const Users = () => {
    const [users, setUsers] = useState();
    const axiosPrivate = useAxiosPrivate();


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const response = await axiosPrivate.get('/users',{signal: controller.signal});
                console.log(response.data.users);
                isMounted && setUsers(response.data.users);
            } catch (error) {
                console.log(error);
            }
        };
        getUsers();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, []);

    return (
        <article>
            <h2>Users</h2>
            {users?.length
                ? (
                    <ul>
                        {users.map((user, i) => (
                            <li key={i}>{user?.firstName}
                            </li>))}
                    </ul>
                ) : (
                    <p>No users found</p>
                )
            }
        </article>
    );
};

export default Users;