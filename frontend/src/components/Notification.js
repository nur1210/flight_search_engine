import {useEffect, useState} from "react";
import useAuth from "../hooks/useAuth";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {Menu} from "@mui/material";
import NotificationItem from "../examples/Items/NotificationItem";
import {createSearchParams, useNavigate} from "react-router-dom";


const ENDPOINT = "http://localhost:8080/ws";

const Notification = ({openMenu, handleCloseMenu}) => {
    const [stompClient, setStompClient] = useState();
    const [messagesReceived, setMessagesReceived] = useState([]);
    const {auth} = useAuth();
    const navigate = useNavigate();

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

    // display the received data
    const onMessageReceived = (data) => {
        const message = JSON.parse(data.body);
        let messages = JSON.parse(localStorage.getItem("messages"));
        if (messages === null) {
            messages = [];
        }
        messages.push(message);
        localStorage.setItem("messages", JSON.stringify(messages));
        setMessagesReceived(messages);
        //setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    const onMessageReceivedPrivate = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    const post = (data) => navigate({
        pathname: '/search-results',
        search: `?${createSearchParams(data)}`
    });

    const handleClick = (event, data) => {
        event.preventDefault();
        post(data);
    };

    return (
        <Menu
            anchorEl={openMenu}
            anchorReference={null}
            anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
            }}
            open={Boolean(openMenu)}
            onClose={handleCloseMenu}
            sx={{mt: 2}}
        >
            {
                messagesReceived.map((message, i) => {
                        return (
                            <NotificationItem key={i} date={message.date} title={[message.title, message.text]} onClick={(e) => handleClick(e, message.queryParam)}/>
                        )
                    }
                )}
        </Menu>

        /*            <Box
                        sx={{
                            display: 'grid',
                            gridAutoFlow: 'row',
                            gridTemplateColumns: 'repeat(5, 1fr)',
                            gridTemplateRows: 'repeat(2, 50px)',
                            gap: 1,
                            zIndex: 1000,
                        }}
                    >
                    <SoftAlert
                        key={i}
                        dismissible={true}
                        sx={{ gridColumn: '5', gridRow: '1 / 3' }}
                    >
                        {message.text}
                    </SoftAlert>
                    </Box>
                )})*/
    );
}

export default Notification