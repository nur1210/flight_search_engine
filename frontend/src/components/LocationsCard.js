import AirportInput from "./AirportInput";
import Card from "@mui/material/Card";
import SoftTypography from "./SoftTypography";
import SoftBox from "./SoftBox";

const LocationsCard = ({ register, errors, setValue, setLocation }) => {
    return(
        <Card className="my-2 card">
            <SoftBox className="card-body">
                <SoftTypography component="label" fontWeight="bold" varient="caption">Locations</SoftTypography>
                <SoftBox className="row">
                    <SoftBox className="col-sm">
                        <AirportInput title={"Origin"} register={register} setValue={setValue} setLocation={setLocation}/>
                        {errors.Origin && <SoftTypography componant="label" variant="caption">{errors.Origin.message}</SoftTypography>}
                    </SoftBox>
                    <SoftBox className="col-sm">
                        <AirportInput title={"Destination"} register={register} setValue={setValue}/>
                        {errors.Destination && <SoftTypography componant="label" variant="caption">{errors.Destination.message}</SoftTypography>}
                    </SoftBox>
                </SoftBox>
            </SoftBox>
        </Card>
    )
}
export default LocationsCard;