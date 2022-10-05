const FlightDurationLabel = ({ departureUTC, arrivalUTC }) => {
    return (
        <label>
            {TimeDifference(departureUTC, arrivalUTC)}
        </label>
    )
}
export default FlightDurationLabel;

function TimeDifference(departureTime, arrivalTime) {
    const departure = new Date(departureTime);
    const arrival = new Date(arrivalTime);
    const diff = arrival - departure;
    const diffInHours = diff / 1000 / 60 / 60;
    const hours = Math.floor(diffInHours);
    const minutes = Math.floor((diffInHours - hours) * 60);
    return `${hours}h ${minutes}m`;
}