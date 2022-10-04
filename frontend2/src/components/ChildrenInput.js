const ChildrenInput = ({children, setChildren}) => {
    return (
        <div className="mb-2">
            <div className="input-group">
                <label htmlFor="children-input" className="input-group-text">Children</label>
                <input
                    type="number"
                    min="0"
                    max="9"
                    className="form-control"
                    id="children-input"
                    aria-describedby="children-label"
                    value={children}
                    onChange={(e) => setChildren(e.target.value)}
                />
            </div>
            <span id="children-label" className="form-text">
                2 to 12 years old
            </span>
        </div>
    )
}
export default ChildrenInput;