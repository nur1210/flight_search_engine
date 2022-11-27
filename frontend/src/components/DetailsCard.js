import TravelClassInput from "./TravelClassInput";
import AdultsInput from "./AdultsInput";
import SoftBox from "./SoftBox";
import Card from "@mui/material/Card";
import {CardBody} from "reactstrap";
import SoftTypography from "./SoftTypography";

const DetailsCard = ({register}) => {
    return(
        <SoftBox mb={2} className={"col"}>
            <Card className="h-100 card">
                <CardBody>
                    <SoftTypography component="label" fontWeight="bold" varient="caption">Details</SoftTypography>
                    <TravelClassInput register={register}/>
                    <label className="form-label">Passengers</label>
                    <AdultsInput defaultValue={1} title={"Adults"} register={register}/>
                    <AdultsInput defaultValue={0} title={"Children"} register={register}/>
                    <AdultsInput defaultValue={0} title={"Infants"} register={register}/>
                </CardBody>
            </Card>
        </SoftBox>
    )
}
export default DetailsCard;