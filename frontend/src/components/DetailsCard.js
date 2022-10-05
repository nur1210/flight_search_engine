import TravelClassInput from "./TravelClassInput";
import AdultsInput from "./AdultsInput";
import ChildrenInput from "./ChildrenInput";
import InfantsInput from "./InfantsInput";

const DetailsCard = (props) => {
    return(
        <div className="mb-2 col">
            <div className="h-100 card">
                <div className="card-body">
                    <h5 className="card-title">Details</h5>
                    <TravelClassInput travelClass={props.travelClass} setTravelClass={props.setTravelClass}/>
                    <label className="form-label">Passengers</label>
                    <AdultsInput adults={props.adults} setAdults={props.setAdults}/>
                    <ChildrenInput children={props.children} setChildren={props.setChildren}/>
                    <InfantsInput infants={props.infants} setInfants={props.setInfants}/>
                </div>
            </div>
        </div>
    )
}
export default DetailsCard;