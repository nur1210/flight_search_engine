import SoftBox from "./SoftBox";
import DefaultInfoCard from "../examples/Cards/InfoCards/DefaultInfoCard";
import SoftButton from "./SoftButton";

const FlightCard = ({flight}) => {
    const redirectToBooking = url => {
        window.open(url, "_blank");
    }

    return (
        <SoftButton variant="text"
                    onClick={() => redirectToBooking(flight.deepLink)}
        >
            <DefaultInfoCard
                icon={"flight"}
                color={"dark"}
                title={`Fly to: ${flight.outboundRoutes[0].arrivalAirport.city}, ${flight.outboundRoutes[0].arrivalAirport.country}`}
                description={`${(flight.outboundRoutes[0].localDepartureTime).split('T')[0].replaceAll('-', "/")} - ${(flight.outboundRoutes[1].localArrivalTime).split('T')[0].replaceAll('-', "/")}`}
                value={`Price: ${flight.price}€`}>
            </DefaultInfoCard>
        </SoftButton>
    )
}

export default FlightCard