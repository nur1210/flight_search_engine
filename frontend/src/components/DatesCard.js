import FlightTypeInput from "./FlightTypeInput";
import DepartureDateInput from "./DepartureDateInput";
import {useState} from "react";
import SoftBox from "./SoftBox";
import Card from "@mui/material/Card";
import {CardBody} from "reactstrap";
import SoftTypography from "./SoftTypography";

const DatesCard = ({ setFlightType, setDepartureDate, setReturnDate, register }) => {
    const [hasDepartureDate, setHasDepartureDate] = useState(true);
    const [hasReturnDate, setHasReturnDate] = useState(true);

    return(
        <SoftBox mb={2} className={"col"}>
            <Card className="h-100 card">
                <CardBody>
                    <SoftTypography component="label" fontWeight="bold" varient="caption">Dates</SoftTypography>
                    {
                        setFlightType
                            ? <FlightTypeInput setFlightType={setFlightType} setHasDepartureDate={setHasDepartureDate} setHasReturnDate={setHasReturnDate} register={register}/>
                            : null
                    }
                    <DepartureDateInput onChange={setDepartureDate} title={"Departure date"} disabled={hasDepartureDate} register={register}/>
                    <DepartureDateInput onChange={setReturnDate} title={"Arrival date"} disabled={hasReturnDate} register={register}/>
                </CardBody>
            </Card>
        </SoftBox>
    )
}
export default DatesCard;