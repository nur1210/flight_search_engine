import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import {createSearchParams, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import SoftBox from "./SoftBox";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import Grid from "@mui/material/Grid";
import SoftButton from "./SoftButton";
import {useEffect} from "react";


const SearchForm = () => {
    const navigate = useNavigate();
    const {register, handleSubmit, formState, formState: {errors, isSubmitSuccessful}, getValues, setValue, reset} = useForm({
        defaultValues: {
            Origin: '',
            Destination: '',
            flightType: '',
            Departure: '',
            Return: '',
            travelClass: '',
            Adults: 1,
            Children: 0,
            Infants: 0
        }
    });


    const post = (data) => navigate({
        pathname: '/search-results',
        search: `?${createSearchParams(data)}`
    });


    const onSubmit = (data) => {
        console.log(data);
        console.log(JSON.stringify(data));
        post(data);
    };

    useEffect(() => {
        if (formState.isSubmitSuccessful) {
            console.log("add")
            reset({
                ...getValues(),
                Origin: '',
                Destination: '',
                flightType: '',
                Departure: '',
                Return: '',
                travelClass: '',
                Adults: 1,
                Children: 0,
                Infants: 0
            });
        }
    }, [isSubmitSuccessful, reset]);


    return (
        <BasicLayout
            title={"Flight Search"}
            description={"Let the journey begin"}
        >
            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
                justifyContent="center"
            >
                <form onSubmit={handleSubmit(onSubmit)} className={"w-75"}>
                    <SoftBox>
                        <SoftBox mt={4} mb={2}>
                            <LocationsCard register={register} setValue={setValue} errors={errors}/>
                        </SoftBox>
                        <SoftBox className="row">
                            <DatesCard register={register}/>
                            <DetailsCard register={register}/>
                        </SoftBox>
                        <SoftButton id="search-button" className="w-100" color={"dark"}
                                    type="submit">Search</SoftButton>
                    </SoftBox>
                </form>
            </Grid>
        </BasicLayout>
    )
};

export default SearchForm;