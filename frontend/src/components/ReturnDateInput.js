const ReturnDateInput = ({ setReturnDate }) => {
    return (
        <div id="return-date" className="mb-2">
                <input
                    type="date"
                    className="form-control"
                    id="return-date-input"
                    aria-describedby="return-date-label"
                    onChange={(e) => setReturnDate(e.target.value)}
                />
        </div>
    );
}
export default ReturnDateInput;
