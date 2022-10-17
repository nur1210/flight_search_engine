const AdultsInput = ({ onChange, title }) => {

    let value;
    if (title === 'Adults') {
        value = 1;
    } else {
        value = 0;
    }
    return(
        <div className="mb-2">
            <div className="input-group">
                <label htmlFor="adults-input" className="input-group-text">{title}</label>
                <input
                    type="number"
                    min="0"
                    max="9"
                    value={value}
                    className="form-control"
                    id="adults-input"
                    aria-describedby="adults-label"
                    onChange={(e) => onChange(e.target.value)}
                />
            </div>
            <span id="adults-label" className="form-text">12 years old and older</span>
        </div>
    )
}
export default AdultsInput;