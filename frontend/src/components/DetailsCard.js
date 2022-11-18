import TravelClassInput from "./TravelClassInput";
import AdultsInput from "./AdultsInput";

const DetailsCard = (props) => {
    return(
        <div className="mb-2 col">
            <div className="h-100 card">
                <div className="card-body">
                    <h5 className="card-title">Details</h5>
                    <TravelClassInput setTravelClass={props.setTravelClass} register={props.register}/>
                    <label className="form-label">Passengers</label>
                    <AdultsInput onChange={props.setAdults} passengers={props.adults} title={"Adults"} register={props.register}/>
                    <AdultsInput onChange={props.setChildren} passengers={props.children} title={"Children"} register={props.register}/>
                    <AdultsInput onChange={props.setInfants} passengers={props.infants} title={"Infants"} register={props.register}/>
                </div>
            </div>
        </div>
    )
}
export default DetailsCard;