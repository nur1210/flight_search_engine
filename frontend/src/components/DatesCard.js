import FlightTypeInput from "./FlightTypeInput";
import DepartureDateInput from "./DepartureDateInput";
import {useState} from "react";
import SoftBox from "./SoftBox";
import Card from "@mui/material/Card";
import {CardBody} from "reactstrap";
import SoftTypography from "./SoftTypography";

const DatesCard = ({register}) => {
    const [hasDepartureDate, setHasDepartureDate] = useState(true);
    const [hasReturnDate, setHasReturnDate] = useState(true);

    return (
        <SoftBox mb={2} className={"col"}>
            <Card className="h-100 card">
                <CardBody>
                    <SoftTypography component="label" fontWeight="bold" varient="caption">Dates</SoftTypography>
                    <FlightTypeInput setHasDepartureDate={setHasDepartureDate}
                                     setHasReturnDate={setHasReturnDate} register={register}/>
                    <DepartureDateInput title={"Departure"} name={"dateFrom"} disabled={hasDepartureDate}
                                        register={register}/>
                    <DepartureDateInput title={"Return"} name={"returnFrom"} disabled={hasReturnDate}
                                        register={register}/>
                </CardBody>
            </Card>
        </SoftBox>
    )
}
export default DatesCard;