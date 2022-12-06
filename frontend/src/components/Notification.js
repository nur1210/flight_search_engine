import {useEffect, useState} from "react";
import useAuth from "../hooks/useAuth";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import ChatMessagesPlaceholder from "./ChatMessagesPlaceholder";

const ENDPOINT = "http://localhost:8080/ws";

const Notification = () => {
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

    // display the received data
    const onMessageReceived = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    const onMessageReceivedPrivate = (data) => {
        const message = JSON.parse(data.body);
        setMessagesReceived(messagesReceived => [...messagesReceived, message]);
    };

    return(
        <ChatMessagesPlaceholder messagesReceived={messagesReceived} />
    )
}

export default Notification