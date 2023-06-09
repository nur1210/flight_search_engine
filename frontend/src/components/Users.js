import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BasicLayout from "./BasicLayout";
import SoftTypography from "./SoftTypography";
import SoftBox from "./SoftBox";
import Card from "@mui/material/Card";
import userTableData from "./userTableData";
import Table from "examples/Tables/Table";
import curved6 from "assets/images/curved-images/curved14.jpg";


const Users = () => {
    const [users, setUsers] = useState([]);
    const [online, setOnline] = useState([]);
    const [loading, setLoading] = useState(false);
    const axiosPrivate = useAxiosPrivate();


    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getUsers = async () => {
            try {
                const res = await axiosPrivate.get('/notifications/online-users');
                setOnline(res.data);
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

    useEffect(() => {
        if (users.length > 0) {
            userTableData.createUserTableData(updatedUsers(users), handleDelete);
            setLoading(true);
        }
    }, [users]);

    const handleDelete = async (id) => {
        try {
            await axiosPrivate.delete(`/users/${id}`).then(
                setUsers(users.filter((user) => user.id !== id))
            );
        } catch (error) {
            console.log(error);
        }
    };

    const updatedUsers = (users) => {
        let updatedUsers = users.map((user) => {
            return {
                ...user,
                online: online.includes(user.email)
            }
        });
        return updatedUsers;
    }



    return (
        <BasicLayout
            light={true}
            title="Admin dashboard"
            image={curved6}
        >
            {
                users.length > 0 && loading ?
                    <>
                        <SoftBox py={3}>
                            <SoftBox mb={3}>
                                <Card>
                                    <SoftBox
                                        sx={{
                                            "& .MuiTableRow-root:not(:last-child)": {
                                                "& td": {
                                                    borderBottom: ({borders: {borderWidth, borderColor}}) =>
                                                        `${borderWidth[1]} solid ${borderColor}`,
                                                },
                                            },
                                        }}
                                    >
                                        <Table columns={userTableData.columns} rows={userTableData.rows}/>
                                    </SoftBox>
                                </Card>
                            </SoftBox>
                        </SoftBox>
                    </> :
                    <SoftTypography>
                        No users
                    </SoftTypography>
            }
        </BasicLayout>
    );
};

export default Users;