import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import SearchForm from "../components/SearchForm";
import FlightsList from "../components/FlightsList";


const FlightSearchPage = () => {
    const [flights, setFlights] = useState([]);
    const [form, setForm] = useState([]);


    /*

        useEffect(() => {
            retrieveFlights();
        }, []);*/


/*    const onSubmit = (form) => {
        console.log(form);
        retrieveFlights(form);

    }*/


/*    const retrieveFlights = (form) => {
        tequilaService.getAll(form.origin,form.destination, form.departureDate, form.returnDate, form.flightType, form.adults, form.children, form.infants, form.travelClass)
            .then(response => {
                setFlights(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
        return (
            <div>
                <FlightsList flights={flights}/>
            </div>
        )
    }*/
/*

    const submitSearch = (e) => {
        e.preventDefault();
        //retrieveFlights();
    }


    return (
        <SearchForm form={form} setForm={setForm} onSubmit={onSubmit}/>
    );
*/

}
export default FlightSearchPage;