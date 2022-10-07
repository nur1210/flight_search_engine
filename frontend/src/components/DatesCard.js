import FlightTypeInput from "./FlightTypeInput";
import DepartureDateInput from "./DepartureDateInput";
import ReturnDateInput from "./ReturnDateInput";

const DatesCard = ({ setFlightType, setDepartureDate, setReturnDate }) => {
    return(
        <div className="mb-2 col">
            <div className="h-100 card">
                <div className="card-body">
                    <h5 className="card-title">Dates</h5>
                    <FlightTypeInput setFlightType={setFlightType}/>
                    <DepartureDateInput onChange={setDepartureDate} title={"Departure date"}/>
                    <DepartureDateInput onChange={setReturnDate} title={"Arrival date"}/>
                </div>
            </div>
        </div>
    )
}
export default DatesCard;