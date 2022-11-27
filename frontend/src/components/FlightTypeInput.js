import { useState } from "react";
import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const FlightTypeInput = ({ setHasDepartureDate, setHasReturnDate, register }) => {
    const options = [
        { value: '', text: "Select Flight Type" },
        { value: 'oneway', text: 'One Way' },
        { value: 'round', text: 'Round Trip' }
    ];

    const [selectedOption, setSelectedOption] = useState();

    const handleChange = (e) => {
        setSelectedOption(e.target.value);
        console.log(e.target.value);
        //setFlightType(e.target.value);
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
        <SoftBox mb={2}>
            <SoftTypography
                component="label"
                vatiant="caption"
                fontWeight="medium"
                fontSize={12}
            >
                Flight Type
            </SoftTypography>
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
        </SoftBox>
    )}
export default FlightTypeInput;