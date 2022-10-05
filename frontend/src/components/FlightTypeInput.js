import {useState} from "react";

const FlightTypeInput = ({ setFlightType }) => {
    const options = [
        { value: 'oneway', text: 'One Way' },
        { value: 'round', text: 'Round Trip' }
    ];

    const [selectedOption, setSelectedOption] = useState(options[0]);

    const handleChange = (e) => {
        setSelectedOption(e.target.value);
        console.log(selectedOption);
        setFlightType(e.target.value);
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
                value={selectedOption.value}
                onChange={handleChange}>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.text}
                    </option>
                ))}
{/*                <option value="oneway" onChange={(e) => setFlightType(e.target.value)}>One-way</option>
                <option value="round" onChange={(e) => setFlightType(e.target.value)}>Round-trip</option>*/}
            </select>
        </div>
    )}
export default FlightTypeInput;