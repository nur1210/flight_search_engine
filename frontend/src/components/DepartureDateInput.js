const DepartureDateInput = ({ onChange, title, disabled, register }) => {

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
                    {...register(title, {
                        onChange:() => (e) => onChange(e.target.value),
                        disabled: disabled,
                        validate: (value) => {
                            const today = new Date();
                            const date = new Date(value);
                            return date > today;
                        }
                    })}
                />
            </div>
        </div>
    )
}
export default DepartureDateInput;