import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {TableRow, TableBody, Paper, Table, TableCell, TableContainer, TableHead} from "@mui/material";


const Users = () => {
    const [users, setUsers] = useState();
    const axiosPrivate = useAxiosPrivate();


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const response = await axiosPrivate.get('/users', {signal: controller.signal});
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
        <BasicLayout title="Users">
            <TableContainer component={Paper}>
                    <Table sx={{minWidth: 650}}>
                            <TableRow>
                                <TableCell align="center">First Name</TableCell>
                                <TableCell align="center">Last Name</TableCell>
                                <TableCell align="center">Email</TableCell>
                            </TableRow>
                        <TableBody>
                            {users && users?.map((user) => (
                                <TableRow
                                    key={user?.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row" align="center">{user?.firstName}</TableCell>
                                    <TableCell component="th" scope="row" align="center">{user?.lastName}</TableCell>
                                    <TableCell component="th" scope="row" align="center">{user?.email}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
            </TableContainer>
{/*            {users?.length
                ? (
                    <ul>
                        {users.map((user, i) => (
                            <li key={i}>{user?.firstName}
                            </li>))}
                    </ul>
                ) : (
                    <p>No users found</p>
                )
            }*/}
        </BasicLayout>
    );
};

export default Users;