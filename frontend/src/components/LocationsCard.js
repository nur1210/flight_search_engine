import AirportInput from "./AirportInput";

const LocationsCard = ({ setOrigin, setDestination }) => {
    return(
        <div className="my-2 card">
            <div className="card-body">
                <h5 className="card-title">Locations</h5>
                <div className="row">
                    <div className="col-sm">
                        <AirportInput onChange={setOrigin} title={"Origin"}/>
                    </div>
                    <div className="col-sm">
                        <AirportInput onChange={setDestination} title={"Destination"}/>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default LocationsCard;