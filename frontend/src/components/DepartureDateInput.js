const DepartureDateInput = ({setDepartureDate}) => {

    return (
        <div id="departure-date" className="mb-2">
            <label
                id="departure-date-label"
                htmlFor="departure-date-input"
                className="form-label">
                Departure date
            </label>
            <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-calendar"></i>
                  </span>
                <input
                    type="date"
                    className="form-control"
                    id="departure-date-input"
                    aria-describedby="departure-date-label"
                    onChange={(e) => setDepartureDate(e.target.value)}
                />
            </div>
        </div>
    )
}
export default DepartureDateInput;