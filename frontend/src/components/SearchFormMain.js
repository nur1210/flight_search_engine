import SoftBox from "./SoftBox";
import LocationsCard from "./LocationsCard";
import DatesCard from "./DatesCard";
import DetailsCard from "./DetailsCard";
import SoftButton from "./SoftButton";
import {useForm} from "react-hook-form";
import {createSearchParams, useNavigate} from "react-router-dom";

const SearchFormMain = ({setLocation}) => {
    const navigate = useNavigate();
    const {register, handleSubmit, formState: {errors}, setValue} = useForm({
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

    return(
        <SoftBox sx={{gridArea: 'main'}}>
            <form onSubmit={handleSubmit(onSubmit)} className={"w-100"}>
                <SoftBox>
                    <SoftBox mt={4} mb={2}>
                        <LocationsCard register={register} setLocation={setLocation} setValue={setValue}
                                       errors={errors}/>
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

export default SearchFormMain;