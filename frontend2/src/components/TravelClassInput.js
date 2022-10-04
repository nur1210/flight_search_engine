import {useState} from "react";

const TravelClassInput = ({ travelClass, setTravelClass }) => {
    const options = [
        { value: 'M', text: 'Economy' },
        { value: 'W', text: 'Premium Economy' },
        { value: 'C', text: 'Business' },
        { value: 'F', text: 'First Class' },
    ];

    const [selectedOption, setSelectedOption] = useState(options[0]);

    const handleChange = (e) => {
        setSelectedOption(e.target.value);
        setTravelClass(e.target.value);
        console.log(travelClass);
    }


    return (
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
                aria-describedby="travel-class-label"
                value={selectedOption.value}
                onChange={handleChange}>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.text}
                    </option>
                ))}
            </select>
        </div>
    )
}
export default TravelClassInput;