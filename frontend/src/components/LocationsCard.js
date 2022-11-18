import AirportInput from "./AirportInput";

const LocationsCard = ({ setOrigin, setDestination, register }) => {

    return(
        <div className="my-2 card">
            <div className="card-body">
                <h5 className="card-title">Locations</h5>
                <div className="row">
                    <div className="col-sm">
                        <AirportInput onChange={setOrigin} title={"Origin"} register={register}/>
                    </div>
                    <div className="col-sm">
                        <AirportInput onChange={setDestination} title={"Destination"} register={register}/>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default LocationsCard;