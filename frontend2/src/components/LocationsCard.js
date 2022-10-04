import OriginInput from "./OriginInput";
import DestinationInput from "./DestinationInput";

const LocationsCard = ({ setOrigin, setDestination }) => {
    return(
        <div className="my-2 card">
            <div className="card-body">
                <h5 className="card-title">Locations</h5>
                <div className="row">
                    <div className="col-sm">
                        <OriginInput setOrigin={setOrigin}/>
                    </div>
                    <div className="col-sm">
                        <DestinationInput setDestination={setDestination}/>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default LocationsCard;