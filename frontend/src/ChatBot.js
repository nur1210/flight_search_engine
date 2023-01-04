import ChatBot from 'react-simple-chatbot';
import {useEffect, useState} from "react";
import TequilaService from "./services/TequilaService";

const Chatbot = () => {
    const [destinations, setDestinations] = useState();
    const [steps, setSteps] = useState([]);

    const config = {
        width: "300px",
        height: "400px",
        floating: true
    };

    const months = [
        {value: '1', label: 'January', trigger: '6'},
        {value: '2', label: 'February', trigger: '6'},
        {value: '3', label: 'March', trigger: '6'},
        {value: '4', label: 'April', trigger: '6'},
        {value: '5', label: 'May', trigger: '6'},
        {value: '6', label: 'June', trigger: '6'},
        {value: '7', label: 'July', trigger: '6'},
        {value: '8', label: 'August', trigger: '6'},
        {value: '9', label: 'September', trigger: '6'},
        {value: '10', label: 'October', trigger: '6'},
        {value: '11', label: 'November', trigger: '6'},
        {value: '12', label: 'December', trigger: '6'}
    ];


    useEffect(() => {
        const getDestination = async () => {
            const response = await TequilaService.getTopTenDestinations("EIN");
            console.log(response.data.destinations);
            setDestinations(response.data.destinations);
        };
        getDestination();

    }, []);

    useEffect(() => {
        console.log(destinations);
        if (Array.isArray(destinations)) {
            console.log('aa');
            const options = destinations.map((destination, i) => ({
                value: i,
                label: `${destination.city}, ${destination.country}`,
                trigger: '4',
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
                    message: 'Where would you like to go?',
                    delay: 1000,
                    trigger: '3',
                },
                {
                    id: '3',
                    delay: 1500,
                    options: options,
                },
                {
                    id: '4',
                    message: 'Select a month',
                    delay: 1000,
                    trigger: '5',
                },
                {
                    id: '5',
                    delay: 1500,
                    options: months,
                },
                {
                    id: '6',
                    message: 'How many days would you like to stay?',
                    delay: 1000,
                    trigger: '7',
                },
                {
                    id: '7',
                    delay: 1500,
                    options: [
                        {value: '1', label: '1-3', trigger: '8'},
                        {value: '2', label: '3-7', trigger: '8'},
                        {value: '2', label: '7-14', trigger: '8'},
                        {value: '2', label: 'custom,', trigger: '8'},
                    ],
                },
                {
                    id: '8',
                    message: 'How many people are travelling?',
                    delay: 1000,
                    trigger: '9',
                },
                {
                    id: '9',
                    delay: 1500,
                    user: true,
                    validator: (value) => {
                        if (value.match(/^[1-9]$/)) {
                            return true;
                        } else {
                            return 'Please enter a number 1-9';
                        }
                    },
                    trigger: '10',
                },
                {
                    id: '10',
                    message: 'Do you want to have stopovers?',
                    delay: 1000,
                    trigger: '11',
                },
                {
                    id: '11',
                    delay: 1500,
                    options: [
                        {value: '0', label: 'Direct only', trigger: '12'},
                        {value: '1', label: 'Max one stop', trigger: '12'},
                        {value: '2', label: 'Max two stops', trigger: '12'},
                    ],
                },
                {
                    id: '12',
                    message: 'Great! I will find you the best flight!',
                    end: true,
                },
            ];


            console.log(options);
            setSteps(steps);

        } else {
            console.log("not array");
            setSteps({options: []});
        }
    }, [destinations]);


    return steps.length > 0 ? (
        <ChatBot steps={steps} {...config}/>
    ) : (
        <div></div>
    );
}

export default Chatbot;