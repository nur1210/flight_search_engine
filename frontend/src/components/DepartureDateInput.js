const DepartureDateInput = ({onChange, title, disabled}) => {

    return (
        <div id="departure-date" className="mb-2">
            <label
                id="departure-date-label"
                htmlFor="departure-date-input"
                className="form-label">
                {title}
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
                    onChange={(e) => onChange(e.target.value)}
                    disabled={disabled}
                />
            </div>
        </div>
    )
}
export default DepartureDateInput;