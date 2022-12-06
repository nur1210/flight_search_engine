import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import {TableRow, TableBody, Table, TableCell, TableContainer} from "@mui/material";
import SoftTypography from "./SoftTypography";
import SoftButton from "./SoftButton";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';


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

    const handleDelete = async (id) => {
        try {
            await axiosPrivate.delete(`/users/${id}`).then(
                setUsers(users.filter((user) => user.id !== id))
            );
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <BasicLayout title="Users">
            {
                users ?
                    <>
                        <TableContainer sx={{mt: 5}}>
                            <Table sx={{minWidth: 650}}>
                                <TableRow>
                                    <TableCell align="center">
                                        <SoftTypography fontWeight="bold" fontSize={18}>
                                            First Name
                                        </SoftTypography>
                                    </TableCell>
                                    <TableCell align="center">
                                        <SoftTypography fontWeight="bold" fontSize={18}>
                                            Last Name
                                        </SoftTypography>
                                    </TableCell>
                                    <TableCell align="center">
                                        <SoftTypography fontWeight="bold" fontSize={18}>
                                            Email
                                        </SoftTypography>
                                    </TableCell>
                                    <TableCell align="center">
                                        <SoftTypography fontWeight="bold" fontSize={18}>
                                            Action
                                        </SoftTypography>
                                    </TableCell>
                                </TableRow>
                                <TableBody>
                                    {users && users?.map((user) => (
                                        <TableRow
                                            key={user?.id}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row"
                                                       align="center">{user?.firstName}</TableCell>
                                            <TableCell component="th" scope="row"
                                                       align="center">{user?.lastName}</TableCell>
                                            <TableCell component="th" scope="row"
                                                       align="center">{user?.email}</TableCell>
                                            <TableCell component="th" scope="row" align="center">
                                                <SoftButton onClick={() => handleDelete(user?.id)} iconOnly circular>
                                                    <DeleteForeverIcon/>
                                                </SoftButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </> :
                    <SoftTypography>
                        No users
                    </SoftTypography>
            }
        </BasicLayout>
    );
};

export default Users;