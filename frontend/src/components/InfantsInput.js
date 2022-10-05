const InfantsInput = ({ infants, setInfants }) => {
    return (
        <div className="mb-2">
            <div className="input-group">
                <label htmlFor="infants-input" className="input-group-text">
                    Infants
                </label>
                <input
                    type="number"
                    min="0"
                    max="9"
                    className="form-control"
                    id="infants-input"
                    aria-describedby="infants-label"
                    value={infants}
                    onChange={(e) => setInfants(e.target.value)}
                />
            </div>
            <span id="infants-label" className="form-text">Up to 2 years old</span>
        </div>
    )
}
export default InfantsInput;