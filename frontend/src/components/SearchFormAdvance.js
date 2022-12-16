import {createSearchParams, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import SoftBox from "./SoftBox";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import SoftButton from "./SoftButton";
import AirportInput from "./AirportInput";

const SearchFormAdvance = () => {
    const navigate = useNavigate();
    const {register, handleSubmit, formState: {errors}, setValue} = useForm({
        defaultValues: {
            flyFrom: '',
            flyTo: '',
            flightType: '',
            dateFrom: '',
            returnFrom: '',
            selectedCabins: '',
            adults: 1,
            children: 0,
            infants: 0
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

    return(
        <SoftBox sx={{gridArea: 'main'}}>
            <form onSubmit={handleSubmit(onSubmit)} className={"w-100"}>
                <SoftBox>
                    <SoftBox mt={4} mb={2}>
                        <AirportInput />
                        <AirportInput />
                    </SoftBox>
                    <SoftBox className="row">
                        <DatesCard register={register}/>
                        <DetailsCard register={register}/>
                    </SoftBox>
                    <SoftButton id="search-button" className="w-100" color={"dark"}
                                type="submit">Search</SoftButton>
                </SoftBox>
            </form>
        </SoftBox>
    )
}

export default SearchFormAdvance