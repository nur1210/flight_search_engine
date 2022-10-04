const AdultsInput = ({ adults, setAdults }) => {
    return(
        <div className="mb-2">
            <div className="input-group">
                <label htmlFor="adults-input" className="input-group-text">Adults</label>
                <input
                    type="number"
                    min="0"
                    max="9"
                    className="form-control"
                    id="adults-input"
                    aria-describedby="adults-label"
                    value={adults}
                    onChange={(e) => setAdults(e.target.value)}
                />
            </div>
            <span id="adults-label" className="form-text">12 years old and older</span>
        </div>
    )
}
export default AdultsInput;