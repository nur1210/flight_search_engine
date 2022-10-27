import {useEffect, useState} from "react";
import UserService from "../services/UserService";

const Users = () => {
    const [users, setUsers] = useState();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const response = await UserService.getAll({signal: controller.signal});
                console.log(response.data);
                isMounted && setUsers(response.data);
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
                            <li key={i}>{user?.name}
                            </li>))}
                    </ul>
                ) : (
                    <p>No users found</p>
                )}
            }
        </article>
    );
};

export default Users;