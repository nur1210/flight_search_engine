import {useEffect, useRef, useState} from "react";
import {GpsFixed} from "@mui/icons-material";
import tequilaService from "../services/TequilaService";

const apiKey = 'AIzaSyAkKp4RUwFXPYyslYaxYSxbRVCiSdhw78E';
const mapApiJs = 'https://maps.googleapis.com/maps/api/js';
const geocodeJson = 'https://maps.googleapis.com/maps/api/geocode/json';


// load google map api js

function loadAsyncScript(src) {
    return new Promise(resolve => {
        const script = document.createElement("script");
        Object.assign(script, {
            type: "text/javascript",
            async: true,
            src
        })
        script.addEventListener("load", () => resolve(script));
        document.head.appendChild(script);
    })
}

const extractAirport = (place) => {

    const airport = {
        city: "",
        country: "",
        plain() {
            const city = this.city ? this.city + ", " : "";
            return city + this.country;
        }
    }

    if (!Array.isArray(place?.address_components)) {
        return airport;
    }

    place.address_components.forEach(component => {
        console.log(component);
        const types = component.types;
        const value = component.long_name;

        if (types.includes("locality")) {
            airport.city = value;
        }

        if (types.includes("country")) {
            airport.country = value;
        }

    });
    return airport;
}

const AirportInput = ({ onChange, title }) => {

    const searchInput = useRef(null);
    const [airport, setAirport] = useState({});


    // init gmap script
    const initMapScript = () => {
        // if script already loaded
        if(window.google) {
            return Promise.resolve();
        }
        const src = `${mapApiJs}?key=${apiKey}&libraries=places&v=weekly`;

        return loadAsyncScript(src);
    }

    // do something on address change
    const onChangeAddress = (autocomplete) => {
        const place = autocomplete.getPlace();
        console.log(place);
        setAirport(extractAirport(place));
        console.log(airport);
    }

    // init autocomplete
    const initAutocomplete = () => {
        if (!searchInput.current) return;

        const autocomplete = new window.google.maps.places.Autocomplete(searchInput.current);
        autocomplete.setFields(["address_component", "geometry"]);
        autocomplete.setTypes(['airport']);
        autocomplete.addListener("place_changed", () => onChangeAddress(autocomplete));
    }




    const reverseGeocode = ({ latitude: lat, longitude: lng}) => {
        const url = `${geocodeJson}?key=${apiKey}&latlng=${lat},${lng}`;
        searchInput.current.value = "Getting your location...";
        fetch(url)
            .then(response => response.json())
            .then(location => {
                const place = location.results[0];
                console.log(place);
                const _airport = extractAirport(place);
                setAirport(_airport);
                searchInput.current.value = _airport.plain();
                console.log(_airport);
                tequilaService.getAirportByCity(_airport.city).then(airport => {
                    console.log(airport);
                    onChange(airport.data.airport.iata);
                });
            })
    }


    const findMyLocation = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                reverseGeocode(position.coords);
            })
        }
    }



    // load map script after mounted
    useEffect(() => {
        initMapScript().then(() => initAutocomplete())
    }, []);

    return (
    <div className="mb-2">
        <label
            id="Airport-label"
            htmlFor="Airport-input"
            className="form-label">
            {title}
        </label>
        <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-pin-map"></i>
                  </span>
            <input
                type="text"
                className="form-control"
                list="Airport-options"
                id="Airport-input"
                placeholder="Location"
                aria-describedby="Airport-label"
                ref={searchInput}
                onChange={(e) => onChange(e.target.value)}
            />
            <button onClick={findMyLocation}><GpsFixed /></button>
            <datalist id="Airport-options"></datalist>
        </div>
    </div>
    )}
export default AirportInput;