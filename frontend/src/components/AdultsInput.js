import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const AdultsInput = ({ title, register, name }) => {
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
                    className="form-control"
                    id="adults-input"
                    aria-describedby="adults-label"
                    {...register(name, {
                        required: true,
                        min: 0,
                        max: 9,
                        pattern: /^[0-9]+$/i
                    })}
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