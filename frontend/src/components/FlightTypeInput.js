import { useState } from "react";

const FlightTypeInput = ({ setFlightType, setHasDepartureDate, setHasReturnDate, register }) => {
    const options = [
        { value: '', text: "Select Flight Type" },
        { value: 'oneway', text: 'One Way' },
        { value: 'round', text: 'Round Trip' }
    ];

    const [selectedOption, setSelectedOption] = useState(options[0]);

    const handleChange = (e) => {
        setSelectedOption(e.target.value);
        console.log(e.target.value);
        setFlightType(e.target.value);
        switch (e.target.value) {
            case 'oneway':
                setHasDepartureDate(false);
                setHasReturnDate(true);
                break;
            case 'round':
                setHasDepartureDate(false);
                setHasReturnDate(false);
                break;
            default:
                setHasDepartureDate(true);
                setHasReturnDate(true);
                break;
        }
    }


    return (
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
                aria-describedby="flight-type-label"
                {...register("flightType", {
                    onChange:(e) => handleChange(e),
                    value: selectedOption,
                    validate: (value) => value !== ""
                })}>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.text}
                    </option>
                ))}
            </select>
        </div>
    )}
export default FlightTypeInput;