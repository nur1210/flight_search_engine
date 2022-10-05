const ReturnDateInput = ({ setReturnDate }) => {
    return (
        <div id="return-date" className="mb-2">
            <label
                id="return-date-label"
                htmlFor="return-date-input"
                className="form-label">
                Return date
            </label>
            <div className="input-group">
                  <span className="input-group-text">
                      <i className="bi-calendar-fill"></i>
                  </span>
                <input
                    type="date"
                    className="form-control"
                    id="return-date-input"
                    aria-describedby="return-date-label"
                    onChange={(e) => setReturnDate(e.target.value)}
                />
            </div>
        </div>
    );
}
export default ReturnDateInput;
