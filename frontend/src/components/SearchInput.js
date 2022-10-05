/*
import {useEffect, useState} from "react";
import tequilaService from "../services/TequilaService";
import FlightList from "./FlightsList";

function SearchInput() {
    const [flights, setFlights] = useState([]);
    const [searchOrigin, setSearchOrigin] = useState("");
    const [searchDestination, setSearchDestination] = useState("");
    const [flightType, setFlightType] = useState("");
    const [departureDate, setDepartureDate] = useState("");
    const [returnDate, setReturnDate] = useState("");
    const [travelClass, setTravelClass] = useState("");
    const [adults, setAdults] = useState(1);
    const [children, setChildren] = useState(0);
    const [infants, setInfants] = useState(0);

    useEffect(() => {
        retrieveFlights();
    }, []);

    //TODO
    function onChangeFlightType(e) {
        setFlightType(e.target.value);
        console.log(flightType);
    }

    const submitSearch = (e) => {
        e.preventDefault();
    }


    return (
        <div className="container-sm">
            <div className="my-2 card">
                <div className="card-body">
                    <h5 className="card-title">Locations</h5>
                    <div className="row">
                        <div className="col-sm">
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
                                        value={searchOrigin}
                                        onChange={(e) => setSearchOrigin(e.target.value)}
                                    />
                                    <datalist id="origin-options"></datalist>
                                </div>
                            </div>
                        </div>
                        <div className="col-sm">
                            <div className="mb-2">
                                <label
                                    id="destination-label"
                                    htmlFor="destination-input"
                                    className="form-label">
                                    Destination
                                </label>
                                <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-pin-map-fill"></i>
                  </span>
                                    <input
                                        type="text"
                                        className="form-control"
                                        list="destination-options"
                                        id="destination-input"
                                        placeholder="Location"
                                        aria-describedby="destination-label"
                                        value={searchDestination}
                                        onChange={(e) => setSearchDestination(e.target.value)}
                                    />
                                    <datalist id="destination-options"></datalist>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="mb-2 col">
                    <div className="h-100 card">
                        <div className="card-body">
                            <h5 className="card-title">Dates</h5>
                            <div className="mb-2">
                                <label
                                    id="flight-type-label"
                                    htmlFor="flight-type-select"
                                    className="form-label">
                                    Flight
                                </label>
                                <select
                                    id="flight-type-select"
                                    className="form-select"
                                    aria-describedby="flight-type-label">
                                    <option value="oneway" onChange={onChangeFlightType}>One-way</option>
                                    <option value="round" onChange={onChangeFlightType}>Round-trip</option>
                                </select>
                            </div>
                            <div id="departure-date" className="mb-2">
                                <label
                                    id="departure-date-label"
                                    htmlFor="departure-date-input"
                                    className="form-label">
                                    Departure date
                                </label>
                                <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-calendar"></i>
                  </span>
                                    <input
                                        type="date"
                                        className="form-control"
                                        id="departure-date-input"
                                        aria-describedby="departure-date-label"
                                        value={departureDate}
                                        onChange={(e) => setDepartureDate(e.target.value)}
                                    />
                                </div>
                            </div>
                            <div id="return-date" className="mb-2">
                                <label
                                    id="return-date-label"
                                    htmlFor="return-date-input"
                                    className="form-label">
                                    Return date
                                </label>
                                <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-calendar-fill"></i>
                  </span>
                                    <input
                                        type="date"
                                        className="form-control"
                                        id="return-date-input"
                                        aria-describedby="return-date-label"
                                        value={returnDate}
                                        onChange={(e) => setReturnDate(e.target.value)}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="mb-2 col">
                    <div className="h-100 card">
                        <div className="card-body">
                            <h5 className="card-title">Details</h5>
                            <div className="mb-2">
                                <label
                                    id="travel-class-label"
                                    htmlFor="travel-class-select"
                                    className="form-label">
                                    Travel class
                                </label>
                                <select
                                    className="form-select"
                                    id="travel-class-select"
                                    aria-describedby="travel-class-label">
                                    <option value="M" onChange={(e) => setTravelClass(e.currentTarget.value)}>Economy
                                    </option>
                                    <option value="W" onChange={(e) => setTravelClass(e.currentTarget.value)}>Premium
                                        Economy
                                    </option>
                                    <option value="C" onChange={(e) => setTravelClass(e.currentTarget.value)}>Business
                                    </option>
                                    <option value="F" onChange={(e) => setTravelClass(e.currentTarget.value)}>First
                                    </option>
                                </select>
                            </div>
                            <label className="form-label">Passengers</label>
                            <div className="mb-2">
                                <div className="input-group">
                                    <label htmlFor="adults-input" className="input-group-text">Adults</label>
                                    <input
                                        type="number"
                                        min="0"
                                        max="9"
                                        className="form-control"
                                        id="adults-input"
                                        aria-describedby="adults-label"
                                        value={adults}
                                        onChange={(e) => setAdults(e.target.value)}
                                    />
                                </div>
                                <span id="adults-label" className="form-text">12 years old and older</span>
                            </div>
                            <div className="mb-2">
                                <div className="input-group">
                                    <label htmlFor="children-input" className="input-group-text">Children</label>
                                    <input
                                        type="number"
                                        min="0"
                                        max="9"
                                        className="form-control"
                                        id="children-input"
                                        aria-describedby="children-label"
                                        value={children}
                                        onChange={(e) => setChildren(e.target.value)}
                                    />
                                </div>
                                <span id="children-label" className="form-text">
                                    2 to 12 years old
                                </span>
                            </div>
                            <div className="mb-2">
                                <div className="input-group">
                                    <label htmlFor="infants-input" className="input-group-text">
                                        Infants
                                    </label>
                                    <input
                                        type="number"
                                        min="0"
                                        max="9"
                                        className="form-control"
                                        id="infants-input"
                                        aria-describedby="infants-label"
                                        value={infants}
                                        onChange={(e) => setInfants(e.target.value)}
                                    />
                                </div>
                                <span id="infants-label" className="form-text">Up to 2 years old</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button id="search-button" className="w-100 btn btn-dark" onClick={submitSearch}>Search</button>
            <FlightList flights={flights} setFlight={submitSearch.list}/>
        </div>
    )
}*/
