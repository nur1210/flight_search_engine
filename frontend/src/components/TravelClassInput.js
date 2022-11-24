import {useState} from "react";
import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const TravelClassInput = ({ setTravelClass, register }) => {
    const options = [
        { value: '', text: 'Select flight class'},
        { value: 'M', text: 'Economy' },
        { value: 'W', text: 'Premium Economy' },
        { value: 'C', text: 'Business' },
        { value: 'F', text: 'First Class' },
    ];

    const [selectedOption, setSelectedOption] = useState();

    const handleChange = (e) => {
        setSelectedOption(e.target.value);
        console.log(e.target.value);
        setTravelClass(e.target.value);
    }


    return (
        <SoftBox mb={2}>
            <SoftTypography
                component="label"
                vatiant="caption"
                fontWeight="medium"
                fontSize={12}
            >
                Travel Class
            </SoftTypography>
            <select
                className="form-select"
                id="travel-class-select"
                aria-describedby="travel-class-label"
                {...register("travelClass", {
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
        </SoftBox>
    )
}
export default TravelClassInput;