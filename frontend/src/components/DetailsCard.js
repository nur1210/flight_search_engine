import TravelClassInput from "./TravelClassInput";
import AdultsInput from "./AdultsInput";
import SoftBox from "./SoftBox";
import Card from "@mui/material/Card";
import {CardBody} from "reactstrap";
import SoftTypography from "./SoftTypography";

const DetailsCard = (props) => {
    return(
        <SoftBox mb={2} className={"col"}>
            <Card className="h-100 card">
                <CardBody>
                    <SoftTypography component="label" fontWeight="bold" varient="caption">Details</SoftTypography>
                    <TravelClassInput setTravelClass={props.setTravelClass} register={props.register}/>
                    <label className="form-label">Passengers</label>
                    <AdultsInput onChange={props.setAdults} passengers={props.adults} title={"Adults"} register={props.register}/>
                    <AdultsInput onChange={props.setChildren} passengers={props.children} title={"Children"} register={props.register}/>
                    <AdultsInput onChange={props.setInfants} passengers={props.infants} title={"Infants"} register={props.register}/>
                </CardBody>
            </Card>
        </SoftBox>
    )
}
export default DetailsCard;