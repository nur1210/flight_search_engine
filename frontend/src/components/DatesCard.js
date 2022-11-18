import FlightTypeInput from "./FlightTypeInput";
import DepartureDateInput from "./DepartureDateInput";
import {useState} from "react";

const DatesCard = ({ setFlightType, setDepartureDate, setReturnDate, register }) => {
    const [hasDepartureDate, setHasDepartureDate] = useState(true);
    const [hasReturnDate, setHasReturnDate] = useState(true);

    return(
        <div className="mb-2 col">
            <div className="h-100 card">
                <div className="card-body">
                    <h5 className="card-title">Dates</h5>
                    {
                        setFlightType
                            ? <FlightTypeInput setFlightType={setFlightType} setHasDepartureDate={setHasDepartureDate} setHasReturnDate={setHasReturnDate} register={register}/>
                            : null
                    }
                    <DepartureDateInput onChange={setDepartureDate} title={"Departure date"} disabled={hasDepartureDate} register={register}/>
                    <DepartureDateInput onChange={setReturnDate} title={"Arrival date"} disabled={hasReturnDate} register={register}/>
                </div>
            </div>
        </div>
    )
}
export default DatesCard;