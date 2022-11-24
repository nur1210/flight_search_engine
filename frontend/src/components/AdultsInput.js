import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const AdultsInput = ({ onChange, passengers, title }) => {

    return(
        <SoftBox mb={2} >
            <SoftBox>
                <SoftTypography
                    component="label"
                    vatiant="caption"
                    fontWeight="medium"
                    fontSize={12}
                >
                    {title}
                </SoftTypography>
                <input
                    type="number"
                    min="0"
                    max="9"
                    value={passengers}
                    className="form-control"
                    id="adults-input"
                    aria-describedby="adults-label"
                    onChange={(e) => onChange(e.target.value)}
                />
            </SoftBox>
            <SoftTypography
                component="span"
                vatiant="caption"
                fontWeight="light"
                fontSize={10}
            >
                12 years old and older
            </SoftTypography>
        </SoftBox>
    )
}
export default AdultsInput;