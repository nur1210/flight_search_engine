import { useState } from "react";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

const FlightTypeInput = ({ setFlightType, setHasDepartureDate, setHasReturnDate }) => {
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
                value={selectedOption}
                onChange={handleChange}>
                {options.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.text}
                    </option>
                ))}
            </select>
           {/* <FormControl sx={{ m: 1, minWidth: 300 }}>
                <InputLabel id="demo-simple-select-autowidth-label">Flight type</InputLabel>
                <Select
                    labelId="flight-type-label"
                    value={selectedOption}
                    onChange={handleChange}
                    autoWidth
                    label="Flight Type"
                >
                    <MenuItem value="">
                        <em>select type</em>
                    </MenuItem>
                    <MenuItem value={"oneway"}>One way</MenuItem>
                    <MenuItem value={"round"}>Round trip</MenuItem>
                </Select>
            </FormControl>*/}
        </div>
    )}
export default FlightTypeInput;