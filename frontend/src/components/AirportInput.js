import {useEffect, useRef} from "react";
import tequilaService from "../services/TequilaService";
import SoftBox from "./SoftBox";

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
        lat: "",
        lng: "",
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

    const cords = place.geometry.viewport;
    airport.lng = Object.values(cords)[1].hi
    console.log(airport.lng);

    airport.lat = Object.values(cords)[0].hi
    console.log(airport.lat);

    return airport;
}

const AirportInput = ({title, register, setValue, setLocation, name, error}) => {
    const searchInput = useRef(null);


    // init gmap script
    const initMapScript = () => {
        // if script already loaded
        if (window.google) {
            return Promise.resolve();
        }
        const src = `${mapApiJs}?key=${apiKey}&libraries=places&v=weekly`;

        return loadAsyncScript(src);
    }

    // do something on address change
    const onChangeAddress = (autocomplete) => {
        const place = autocomplete.getPlace();
        console.log(place);
        const _airport = extractAirport(place);
        tequilaService.getAirportByCords(_airport.lat, _airport.lng).then((data) => {
            console.log(data);
            setValue(name, data.data.airport.iata);
        })
    }

    // init autocomplete
    const initAutocomplete = (e) => {
        if (!searchInput.current) return;

        const autocomplete = new window.google.maps.places.Autocomplete(searchInput.current);
        autocomplete.setFields(["address_component", "geometry"]);
        autocomplete.setTypes(['airport']);
        autocomplete.addListener("place_changed", () => onChangeAddress(autocomplete));
    }


    const reverseGeocode = ({latitude: lat, longitude: lng}) => {
        const url = `${geocodeJson}?key=${apiKey}&latlng=${lat},${lng}`;
        searchInput.current.value = "Getting your location...";
        fetch(url)
            .then(response => response.json())
            .then(location => {
                console.log(location);
                const place = location.results[0];
                console.log(place);
                const _airport = extractAirport(place);
                searchInput.current.value = _airport.plain();
                console.log(_airport);
                tequilaService.getAirportByCity(_airport.city).then(airport => {
                    console.log(airport);
                    setValue(name, airport.data.airport.iata);
                })
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
        initMapScript().then(() => initAutocomplete());
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                setLocation(position.coords);
            })
        }
    }, []);

    return (
        <SoftBox mb={2}>
                <input
                    ref={searchInput}
                    type="text"
                    className="form-control"
                    list={`Airport-options-${name}`}
                    id="Airport-input"
                    data-cy={`${name}-input`}
                    placeholder={title}
                    aria-describedby="Airport-label"
                    onChange={initAutocomplete}
                    style={{borderRadius: 0, height: 39.5}}
                />
                <input
                    type={"hidden"}
                    {...register(name, {
                        validate: value => {
                            if (!value) return "Repeat password is required";
                        }
                    })}
                />
                {/*<SoftButton variant={"text"} color={"dark"} onClick={findMyLocation}><GpsFixedIcon/></SoftButton>*/}
                <datalist id={`Airport-options-${title}`}></datalist>
        </SoftBox>

    )
}
export default AirportInput;