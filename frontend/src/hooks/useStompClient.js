import { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import SockJS from "sockjs-client";
import useAuth from "./useAuth";

const useClient = () => {
    const ENDPOINT = "http://localhost:8080/ws";
    const [client, setClient] = useState(null);
    const [privateClient, setPrivateClient] = useState(null);
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
        setClient(stompClient);
        setPrivateClient(stompClientPrivate);
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

    return { client, privateClient, messagesReceived };
};

export default useClient;
