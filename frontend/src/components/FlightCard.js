import SoftBox from "./SoftBox";
import DefaultInfoCard from "../examples/Cards/InfoCards/DefaultInfoCard";

const FlightCard = ({flight}) => {

    return(
        <SoftBox mb={2} mr={1}>
            <DefaultInfoCard
                icon={"paid"}
                color={"secondary"}
                title={`Fly to: ${flight.route[0].arrivalAirport.city}, ${flight.route[0].arrivalAirport.country}`}
                description={`${(flight.route[0].localDepartureTime).split('T')[0].replaceAll('-', "/")} - ${(flight.route[1].localArrivalTime).split('T')[0].replaceAll('-', "/")}`}
                value={`Price: ${flight.price}€`}>
            </DefaultInfoCard>
        </SoftBox>
    )
}

export default FlightCard