import SoftBox from "./SoftBox";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import SoftButton from "./SoftButton";
import {useForm} from "react-hook-form";
import {createSearchParams, useNavigate} from "react-router-dom";
import {
    Card, CardContent,
    FormControl,
    FormControlLabel,
    Grid, IconButton,
    InputAdornment,
    Menu,
    MenuItem,
    Radio,
    RadioGroup, Select,
    TextField
} from "@mui/material";
import SoftTypography from "./SoftTypography";
import SoftInput from "./SoftInput";
import {Add, Remove} from "@mui/icons-material";
import React, {useState} from "react";
import AirportInput from "./AirportInput";
import {toast, ToastContainer} from "react-toastify";

const SearchFormMain = ({setLocation}) => {
    const navigate = useNavigate();
    const error = (message) => toast.info(message);
    const {register, handleSubmit, watch, formState: {errors}, setValue, getValues} = useForm({
        defaultValues: {
            flyFrom: '',
            flyTo: '',
            flightType: '',
            dateFrom: '',
            returnFrom: '',
            selectedCabins: 'M',
            adults: 1,
            children: 0,
            infants: 0,
            maxSectorStopovers: 0,
        }
    });
    const [anchorEl, setAnchorEl] = useState(null);


    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handlePassengersChange = (increment) => {
        const adults = getValues('adults');
        if (adults + increment >= 1) {
            setValue('adults', adults + increment);
        }
    };

    const cabinClasses = {
        M: 'Economy',
        W: 'Premium Economy',
        C: 'Business',
        F: 'First Class',
    };

    const post = (data) => navigate({
        pathname: '/search-results',
        search: `?${createSearchParams(data)}`
    });


    const onSubmit = (data) => {
        console.log(data);
        post(data);
    };

    const onError = async (errors) => {
        console.log(errors);
        error(errors[Object.keys(errors)[0]].message);
    };

    return (
        <SoftBox sx={{gridArea: 'main'}}>
            <form onSubmit={handleSubmit(onSubmit, onError)}>
                <Grid
                    container
                    item={true}
                    direction="column"
                    alignContent={"center"}
                >
                    <Card
                        sx={{backgroundColor: "rgba(255,255,255,0.51)"}}
                    >
                        <CardContent>
                            <Grid
                                display={"flex"}
                                justifyContent={"start"}
                            >
                                <SoftBox mt={1} ml={2}>
                                    <Grid
                                        display={"flex"}
                                        flexDirection={"row"}
                                    >
                                        <SoftBox mr={2} mb={1}>
                                            <FormControl>
                                                <RadioGroup
                                                    aria-labelledby="demo-radio-buttons-group-label"
                                                    defaultValue='round'
                                                    name="radio-buttons-group"
                                                    sx={{display: 'flex', flexDirection: 'row'}}
                                                    {...register("flightType", {required: "Please select a flight type"})}
                                                >
                                                    <FormControlLabel value="round" control={<Radio/>}
                                                                      label="Round trip"/>
                                                    <FormControlLabel value="oneway" control={<Radio/>}
                                                                      label="One way"/>
                                                </RadioGroup>
                                            </FormControl>
                                        </SoftBox>
                                    </Grid>
                                </SoftBox>
                            </Grid>
                            <SoftBox mt={1} mr={1} ml={1}>
                                <Grid
                                    display={"flex"}
                                    justifyContent={"center"}
                                >
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            From
                                        </SoftTypography>
                                        <AirportInput name={"flyFrom"} title={"Origin"} register={register} error={error}
                                                      setValue={setValue}
                                                      setLocation={setLocation}/>
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            To
                                        </SoftTypography>
                                        <AirportInput name={"flyTo"} title={"Destination"} register={register} error={error}
                                                      setValue={setValue}/>
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>Depart
                                        </SoftTypography>
                                        <SoftInput
                                            type={"date"}
                                            {...register("dateFrom", {
                                                validate: (value) => {
                                                    const today = new Date();
                                                    const date = new Date(value);
                                                    return date > today || "Departure date must be in the future";
                                                }
                                            })}
                                            sx={{borderRadius: 0}}
                                        />
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>Return
                                        </SoftTypography>
                                        <SoftInput
                                            type={"date"}
                                            {...register("returnFrom", {
                                                validate: (value) => {
                                                    const dateFrom = new Date(watch('dateFrom'));
                                                    const date = new Date(value);
                                                    return date > dateFrom || "Return date must be after departure date";
                                                }
                                            })}
                                            sx={{borderRadius: 0}}
                                        />
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10} textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            Cabin class & travelers
                                        </SoftTypography>
                                        <div>
                                            <SoftInput
                                                aria-controls="simple-menu"
                                                onClick={handleClick}
                                                value={`${getValues('adults')} passengers, ${cabinClasses[getValues('selectedCabins')]}`}
                                                sx={{borderRadius: 0}}
                                            />
                                            <Menu
                                                id="simple-menu"
                                                anchorEl={anchorEl}
                                                keepMounted
                                                open={Boolean(anchorEl)}
                                                onClose={handleClose}
                                                sx={{maxWidth: 250}}
                                            >
                                                <MenuItem style={{
                                                    backgroundColor: 'transparent',
                                                    '&:hover': {backgroundColor: 'transparent'}
                                                }}>
                                                    <TextField
                                                        label="Passengers"
                                                        onChange={handlePassengersChange}
                                                        InputProps={{
                                                            endAdornment: (
                                                                <InputAdornment position="end">
                                                                    <IconButton
                                                                        aria-label="decrement passengers"
                                                                        onClick={() => handlePassengersChange(-1)}
                                                                    >
                                                                        <Remove/>
                                                                    </IconButton>
                                                                    <IconButton
                                                                        aria-label="increment passengers"
                                                                        onClick={() => handlePassengersChange(1)}
                                                                    >
                                                                        <Add/>
                                                                    </IconButton>
                                                                </InputAdornment>
                                                            ),
                                                        }}
                                                        inputProps={{readOnly: true}}
                                                        {...register("adults", {valueAsNumber: true})}
                                                    />
                                                </MenuItem>
                                                <MenuItem style={{
                                                    display: 'flex',
                                                    alignItems: 'center',
                                                    justifyContent: 'center',
                                                    backgroundColor: 'transparent',
                                                    '&:hover': {backgroundColor: 'transparent'}
                                                }}>
                                                    <Select
                                                        label="Cabin class"
                                                        defaultValue="M"
                                                        style={{
                                                            display: 'flex',
                                                            alignItems: 'center',
                                                            justifyContent: 'center'
                                                        }}
                                                        {...register("selectedCabins")}
                                                    >
                                                        <MenuItem value="M">Economy</MenuItem>
                                                        <MenuItem value="W">Premium Economy</MenuItem>
                                                        <MenuItem value="C">Business</MenuItem>
                                                        <MenuItem value="F">First Class</MenuItem>
                                                    </Select>
                                                </MenuItem>
                                            </Menu>
                                        </div>
                                    </SoftBox>
                                </Grid>
                            </SoftBox>
                            <SoftBox mb={1} mr={1}>
                                <Grid
                                    display={"flex"}
                                    justifyContent={"end"}
                                >
                                    <SoftBox mt={3}>
                                        <SoftButton variant="gradient" color={"dark"} size={"large"}
                                                    type="submit">Search</SoftButton>
                                    </SoftBox>
                                </Grid>
                            </SoftBox>
                        </CardContent>
                    </Card>
                </Grid>
            </form>
            <ToastContainer position="bottom-center" autoClose={3000}/>
        </SoftBox>
    )
}

export default SearchFormMain;