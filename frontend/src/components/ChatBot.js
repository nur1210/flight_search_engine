import ChatBot from 'react-simple-chatbot';
import {useEffect, useState} from "react";
import TequilaService from "../services/TequilaService";
import ChatResult from "./ChatResult";
import {ThemeProvider} from 'styled-components';
import FlightMessage from "./FlightMessage";

const Chatbot = ({airport}) => {
    const [destinations, setDestinations] = useState();
    const [steps, setSteps] = useState([]);

    const config = {
        width: "300px",
        height: "400px",
        floating: true
    };

    const theme = {
        background: '#f5f8fb',
        headerBgColor: '#344767',
        headerFontColor: '#fff',
        headerFontSize: '15px',
        botBubbleColor: '#ffffff',
        botFontColor: '#344767',
        userBubbleColor: '#fff',
        userFontColor: '#344767',
    };

    const months = [
        {value: 0, label: 'January', trigger: '7'},
        {value: 1, label: 'February', trigger: '7'},
        {value: 2, label: 'March', trigger: '7'},
        {value: 3, label: 'April', trigger: '7'},
        {value: 4, label: 'May', trigger: '7'},
        {value: 5, label: 'June', trigger: '7'},
        {value: 6, label: 'July', trigger: '7'},
        {value: 7, label: 'August', trigger: '7'},
        {value: 8, label: 'September', trigger: '7'},
        {value: 9, label: 'October', trigger: '7'},
        {value: 10, label: 'November', trigger: '7'},
        {value: 11, label: 'December', trigger: '7'}
    ];


    useEffect(() => {
        if (airport === null) return;
        const getDestination = async () => {
            const response = await TequilaService.getTopTenDestinations(airport.iata);
            console.log(response.data.destinations);
            setDestinations(response.data.destinations);
        };
        getDestination();

    }, [airport]);

    useEffect(() => {
        console.log(destinations);
        if (Array.isArray(destinations)) {
            const options = destinations.map(destination => ({
                value: destination.cityCode,
                label: `${destination.city}, ${destination.country}`,
                trigger: '5',
            }));

            const steps = [
                {
                    id: '1',
                    message: 'I am here to help you find your next flight!',
                    delay: 1000,
                    trigger: '2',
                },
                {
                    id: '2',
                    options: [
                        {value: '1', label: 'I am ready!', trigger: '3'},
                        {value: '2', label: 'Not now', trigger: '14'},
                    ],
                },
                {
                    id: '3',
                    message: 'Where would you like to go?',
                    delay: 500,
                    trigger: 'destination',
                },
                {
                    id: 'destination',
                    options: options,
                },
                {
                    id: '5',
                    message: 'Select a month',
                    delay: 500,
                    trigger: 'month',
                },
                {
                    id: 'month',
                    options: months,
                },
                {
                    id: '7',
                    message: 'How many days would you like to stay?',
                    delay: 500,
                    trigger: 'duration',
                },
                {
                    id: 'duration',
                    options: [
                        {value: 1, label: '1-3', trigger: '9'},
                        {value: 2, label: '3-7', trigger: '9'},
                        {value: 3, label: '7-14', trigger: '9'},
                    ],
                },
                {
                    id: '9',
                    message: 'How many people are travelling?',
                    delay: 500,
                    trigger: 'passengers',
                },
                {
                    id: 'passengers',
                    user: true,
                    validator: (value) => {
                        if (value.match(/^[1-9]$/)) {
                            return true;
                        } else {
                            return 'Please enter a number 1-9';
                        }
                    },
                    trigger: '11',
                },
                {
                    id: '11',
                    message: 'Do you want to have stopovers?',
                    delay: 500,
                    trigger: 'maxStopovers',
                },
                {
                    id: 'maxStopovers',
                    options: [
                        {value: '0', label: 'Direct only', trigger: '13'},
                        {value: '1', label: 'Max one stop', trigger: '13'},
                        {value: '2', label: 'Max two stops', trigger: '13'},
                    ],
                },
                {
                    id: '13',
                    message: 'Great! I will find you the best flight!',
                    trigger: '14',
                },
                {
                    id: '14',
                    component: <ChatResult
                        airport={airport}
                    />,
                    waitAction: true,
                },
            ];

            setSteps(steps);
        }
    }, [destinations]);




    return steps.length > 0 ? (
        <ThemeProvider theme={theme}>
            <ChatBot
                headerTitle="Flight Finder"
                steps={steps}
                {...config}
            />
        </ThemeProvider>
    ) : (
        <div></div>
    );
}

export default Chatbot;