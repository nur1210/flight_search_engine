import SoftBox from "./SoftBox";
import {Card, Grid, MenuItem, MenuList, Paper} from "@mui/material";
import SoftButton from "./SoftButton";
import SoftInput from "./SoftInput";
import SoftTypography from "./SoftTypography";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import {useEffect, useState} from "react";
import SendMessagePlaceholder from "./SendMessagePlaceholder";
import ChatMessagesPlaceholder from "./ChatMessagesPlaceholder";
import useAuth from "../hooks/useAuth";

const ENDPOINT = "http://localhost:8080/ws";

const Missing = () => {
    const [stompClient, setStompClient] = useState();
    const [messagesReceived, setMessagesReceived] = useState([]);
    const {auth} = useAuth();


    useEffect(() => {
        // use SockJS as the websocket client
        const socket = SockJS(ENDPOINT);
        // Set stomp to use websockets
        const stompClient = Stomp.over(socket);
        // connect to the backend
        stompClient.connect({}, () => {
            // subscribe to the backend
            stompClient.subscribe('/topic/notifications', onMessageReceived, {id: auth.email}, (data) => {
            });
        });

        const socketPrivate = SockJS(ENDPOINT);

        const stompClientPrivate = Stomp.over(socketPrivate);
        // connect to the backend
        stompClientPrivate.connect({email: auth.email}, () => {
            // subscribe to the backend
            stompClientPrivate.subscribe(`/user/topic/specific-notifications`, onMessageReceivedPrivate, {id: auth.email}, (data) => {
            });
        });

        // maintain the client for sending and receiving
        setStompClient(stompClient);
    }, []);

    // send the data using Stomp
    const sendMessage = (newMessage) => {
        const payload = { 'to': newMessage.to, 'text': newMessage.text };
        if (payload.to !== '') {
            stompClient.send('/app/specific-notification', {}, JSON.stringify({'text':payload.text, 'to':payload.to}));
        } else {
            stompClient.send(`/app/notification`, {}, JSON.stringify({'text':payload.text}));
        }
    };

    // display the received data
    const onMessageReceived = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    const onMessageReceivedPrivate = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };


    return (/*
        <article style={{ padding: "100px" }}>
            <h1>Oops!</h1>
            <p>Page Not Found</p>
            <SoftBox
             className="flexGrow">
                <Link to="/">Visit Our Homepage</Link>
            </SoftBox
            >
        </article>*/

        <BasicLayout
            title={"Let the journey begin"}
        >
            <div>
                <SendMessagePlaceholder onMessageSend={sendMessage} />
                <br></br>
                <ChatMessagesPlaceholder messagesReceived={messagesReceived} />
            </div>
            <Grid
                container
                direction="column"
                alignContent={"center"}
                sx={{minWidth: 650}}
            >
                <Card className={"my-2 card"}>
                    <SoftBox className={"card-body"}>
                        <Grid
                            display={"flex"}
                            justifyContent={"start"}
                        >
                            <SoftBox mt={2} ml={1}>
                                <Grid
                                    display={"flex"}
                                    flexDirection={"row"}
                                >
                                    <SoftBox mr={2} mb={1}>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            <input type={"radio"} value={"round"}/>
                                            Round trip
                                        </SoftTypography>
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            <input type={"radio"} value={"oneway"}/>
                                            Oneway
                                        </SoftTypography>
                                    </SoftBox>
                                </Grid>
                            </SoftBox>
                        </Grid>
                        <SoftBox mt={1} mr={1} ml={1}>
                            <Grid
                                display={"flex"}
                                justifyContent={"center"}
                            >
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        From
                                    </SoftTypography>
                                    <SoftInput/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        To
                                    </SoftTypography>
                                    <SoftInput/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>Depart
                                    </SoftTypography>
                                    <SoftInput type={"date"}/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>Return
                                    </SoftTypography>
                                    <SoftInput type={"date"}/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10} textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        Cabin class & travelers
                                    </SoftTypography>
                                    <Paper>
                                        <MenuList>
                                            <MenuItem>Profile</MenuItem>
                                            <MenuItem>My account</MenuItem>
                                            <MenuItem>Logout</MenuItem>
                                        </MenuList>
                                    </Paper>
                                </SoftBox>
                            </Grid>
                        </SoftBox>
                        <SoftBox mb={1} mr={1}>
                            <Grid
                                display={"flex"}
                                justifyContent={"end"}
                            >
                                <SoftBox mt={3}>
                                    <SoftButton color={"dark"} size={"large"}>Search</SoftButton>
                                </SoftBox>
                            </Grid>
                        </SoftBox>
                    </SoftBox>
                </Card>
            </Grid>
        </BasicLayout>
    )
}

export default Missing;