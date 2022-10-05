const DestinationInput = ({ setDestination }) => {
    return (
        <div className="mb-2">
            <label
                id="destination-label"
                htmlFor="destination-input"
                className="form-label">
                Destination
            </label>
            <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-pin-map-fill"></i>
                  </span>
                <input
                    type="text"
                    className="form-control"
                    list="destination-options"
                    id="destination-input"
                    placeholder="Location"
                    aria-describedby="destination-label"
                    onChange={(e) => setDestination(e.target.value)}
                />
                <datalist id="destination-options"></datalist>
            </div>
        </div>
    )}
export default DestinationInput;