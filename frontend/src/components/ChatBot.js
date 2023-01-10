import ChatBot from 'react-simple-chatbot';
import {useEffect, useState} from "react";
import TequilaService from "../services/TequilaService";
import ChatResult from "./ChatResult";
import {ThemeProvider} from 'styled-components';
import FlightMessage from "./FlightMessage";

const Chatbot = ({airport}) => {
    const [destinations, setDestinations] = useState();
    const [steps, setSteps] = useState([]);
    const [flights, setFlights] = useState([]);

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

    const updateSteps = newSteps => {
        setSteps(newSteps);
    }


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
                    delay: 1000,
                    trigger: 'destination',
                },
                {
                    id: 'destination',
                    options: options,
                },
                {
                    id: '5',
                    message: 'Select a month',
                    delay: 1000,
                    trigger: 'month',
                },
                {
                    id: 'month',
                    options: months,
                },
                {
                    id: '7',
                    message: 'How many days would you like to stay?',
                    delay: 1000,
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
                    delay: 1000,
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
                    delay: 1000,
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
                        setFlights={setFlights}
                        flights={flights}
                        onSearchCompleted={onSearchCompleted}
                    />,
                    waitAction: true,
                },
                {
                    id: '15',
                    message: 'Here are your results!',
                    trigger: '16',
                },
                {
                    id: '16',
                    options: [
                        {value: '1', label: 'Yes', trigger: '3'},
                        {value: '2', label: 'No', trigger: '17'},
                    ],
                },
                {
                    id: '17',
                    message: 'Thank you for using our service!',
                    end: true,
                },
            ];

            setSteps(steps);
        }
    }, [destinations]);

    useEffect(() => {
        console.log(flights);
        if (flights.length > 0) {
            const steps = [
                {
                    id: '15',
                    component: <FlightMessage flights={flights}/>,
                    trigger: '16',
                },
                {
                    id: '16',
                    options: [
                        {value: '1', label: 'Yes', trigger: '3'},
                        {value: '2', label: 'No', trigger: '17'},
                    ],
                },
                {
                    id: '17',
                    message: 'Thank you for using our service!',
                    end: true,
                },
            ];
            updateSteps(steps);
        }
    }, [flights]);

    const onSearchCompleted = () => {
        console.log("search completed");
        const newSteps = [
            ...steps,
            {
                id: '16',
                message: 'Here are the flights that match your criteria:',
                trigger: '18',
            },
/*            {
                id: '17',
                component: <ChatResult flights={flights} setFlights={setFlights}/>,
                asMessage: true,
                trigger: '18',
            },*/
            {
                id: '18',
                options: [
                    {value: '1', label: 'Start over', trigger: '1'},
                    {value: '2', label: 'Exit', trigger: '19'},
                ],
            },
            {
                id: '19',
                message: 'Thank you for using our service!',
                end: true,
            },
        ];

        setSteps(newSteps);
    };


    return steps.length > 0 ? (
        <ThemeProvider theme={theme}>
            <ChatBot
                headerTitle="Flight Finder"
                steps={steps}
                updateSteps={updateSteps}
                {...config}
            />
        </ThemeProvider>
    ) : (
        <div></div>
    );
}

export default Chatbot;