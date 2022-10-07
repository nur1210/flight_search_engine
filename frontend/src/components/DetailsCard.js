import TravelClassInput from "./TravelClassInput";
import AdultsInput from "./AdultsInput";

const DetailsCard = (props) => {
    return(
        <div className="mb-2 col">
            <div className="h-100 card">
                <div className="card-body">
                    <h5 className="card-title">Details</h5>
                    <TravelClassInput travelClass={props.travelClass} setTravelClass={props.setTravelClass}/>
                    <label className="form-label">Passengers</label>
                    <AdultsInput onChange={props.setAdults} title={"Adults"}/>
                    <AdultsInput onChange={props.setChildren} title={"Children"}/>
                    <AdultsInput onChange={props.setInfants} title={"Infants"}/>
                </div>
            </div>
        </div>
    )
}
export default DetailsCard;