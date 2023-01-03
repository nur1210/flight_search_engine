import SoftBox from "./SoftBox";
import {
    Button,
    Card,
    FormControl,
    FormControlLabel,
    Grid, IconButton, Input, InputAdornment, Menu,
    MenuItem,
    MenuList,
    Paper,
    Radio,
    RadioGroup, Select, TextField
} from "@mui/material";
import SoftButton from "./SoftButton";
import SoftInput from "./SoftInput";
import SoftTypography from "./SoftTypography";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import React, {useEffect, useState} from "react";
import SendMessagePlaceholder from "./SendMessagePlaceholder";
import ChatMessagesPlaceholder from "./ChatMessagesPlaceholder";
import useAuth from "../hooks/useAuth";
import {Add, Remove} from "@mui/icons-material";

const ENDPOINT = "http://localhost:8080/ws";

const Missing = () => {
    const [stompClient, setStompClient] = useState();
    const [messagesReceived, setMessagesReceived] = useState([]);
    const {auth} = useAuth();
    const [anchorEl, setAnchorEl] = useState(null);
    const [passengers, setPassengers] = useState(1);
    const [cabinClass, setCabinClass] = useState('economy');

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handlePassengersChange = (event) => {
        setPassengers(event.target.value);
    };

    const handleCabinClassChange = (event) => {
        setCabinClass(event.target.value);
    };


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
        const payload = {'to': newMessage.to, 'text': newMessage.text};
        if (payload.to !== '') {
            stompClient.send('/app/specific-notification', {}, JSON.stringify({
                'text': payload.text,
                'to': payload.to
            }));
        } else {
            stompClient.send(`/app/notification`, {}, JSON.stringify({'text': payload.text}));
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
                                        <FormControl>
                                            <RadioGroup
                                                aria-labelledby="demo-radio-buttons-group-label"
                                                defaultValue='round'
                                                name="radio-buttons-group"
                                                sx={{display: 'flex', flexDirection: 'row'}}
                                            >
                                                <FormControlLabel value="round" control={<Radio/>} label="Round trip"/>
                                                <FormControlLabel value="oneway" control={<Radio/>} label="One way"/>
                                            </RadioGroup>
                                        </FormControl>
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
                                    <div>
                                        <SoftInput
                                            aria-controls="simple-menu"
                                            onClick={handleClick}
                                            value={`${passengers} passengers, ${cabinClass}`}
                                        />
                                        <Menu
                                            id="simple-menu"
                                            anchorEl={anchorEl}
                                            keepMounted
                                            open={Boolean(anchorEl)}
                                            onClose={handleClose}
                                            sx={{width: 250}}
                                        >
                                            <MenuItem style={{ backgroundColor: 'transparent', '&:hover': { backgroundColor: 'transparent' } }}>
                                                <TextField
                                                    label="Passengers"
                                                    value={passengers}
                                                    onChange={handlePassengersChange}
                                                    InputProps={{
                                                        endAdornment: (
                                                            <InputAdornment position="end">
                                                                <IconButton
                                                                    aria-label="decrement passengers"
                                                                    onClick={() => setPassengers(passengers - 1)}
                                                                >
                                                                    <Remove/>
                                                                </IconButton>
                                                                <IconButton
                                                                    aria-label="increment passengers"
                                                                    onClick={() => setPassengers(passengers + 1)}
                                                                >
                                                                    <Add/>
                                                                </IconButton>
                                                            </InputAdornment>
                                                        ),
                                                    }}
                                                    inputProps={{readOnly: true}}
                                                />
                                            </MenuItem>
                                            <MenuItem style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', backgroundColor: 'transparent', '&:hover': { backgroundColor: 'transparent' } }}>
                                                <Select
                                                    label="Cabin class"
                                                    value={cabinClass}
                                                    onChange={handleCabinClassChange}
                                                    style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
                                                >
                                                    <MenuItem value="economy">Economy</MenuItem>
                                                    <MenuItem value="business">Business</MenuItem>
                                                    <MenuItem value="first">First</MenuItem>
                                                    <MenuItem value="premium">Premium</MenuItem>
                                                </Select>
                                            </MenuItem>
                                        </Menu>
                                    </div>
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