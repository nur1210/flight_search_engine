import {useEffect, useRef, useState} from "react";
import {GpsFixed} from "@mui/icons-material";

const apiKey = 'AIzaSyAkKp4RUwFXPYyslYaxYSxbRVCiSdhw78E';
const mapApiJs = 'https://maps.googleapis.com/maps/api/js';
const geocodeJson = 'https://maps.googleapis.com/maps/api/geocode/json';
const nearbySearchJson = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json';


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
        iata: "",
        name: "",
        city: "",
        country: "",
        plain() {
            const iata = this.iata ? this.iata + ", " : "";
            const name = this.name ? this.name + ", " : "";
            const city = this.city ? this.city + ", " : "";
            return iata + name + city + this.country;
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

        if (types.includes("name")) {
            airport.name = value;
        }

        if (types.includes("administrative_area_level_1")) {
            airport.city = value;
        }

        if (types.includes("country")) {
            airport.country = value;
        }

    });
    console.log(airport);
    return airport;
}

const OriginInput = ({ setOrigin }) => {

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
        //autocomplete.setFields(["address_component", "geometry"]);
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
            })
    }


    const findMyLocation = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                //reverseGeocode(position.coords);
                FindClosestAirport(position.coords);
            })
        }
    }

    const FindClosestAirport = ({ latitude: lat, longitude: lng}) => {
        const URL = `https://cors-anywhere.herokuapp.com/${nearbySearchJson}?key=${apiKey}&location=${lat},${lng}&radius=5000&type=airport`;
        fetch(URL)
            .then(data=> {
            return data.json()
        }).then(jsonData => {
            console.log(jsonData)
            let place = jsonData.results;
            let _airport = extractAirport(place);
            console.log(_airport);
        }).catch(error=> {
            console.log(error);
        })
    }


    // load map script after mounted
    useEffect(() => {
        initMapScript().then(() => initAutocomplete())
    }, []);

    return (
    <div className="mb-2">
        <label
            id="origin-label"
            htmlFor="origin-input"
            className="form-label">
            Origin
        </label>
        <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-pin-map"></i>
                  </span>
            <input
                type="text"
                className="form-control"
                list="origin-options"
                id="origin-input"
                placeholder="Location"
                aria-describedby="origin-label"
                ref={searchInput}
                onChange={(e) => setOrigin(e.target.value)}
            />
            <button onClick={findMyLocation}><GpsFixed /></button>
            <datalist id="origin-options"></datalist>
        </div>
    </div>
    )}
export default OriginInput;