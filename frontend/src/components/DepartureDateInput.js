import SoftBox from "./SoftBox";
import SoftTypography from "./SoftTypography";

const DepartureDateInput = ({ title, disabled, register, name }) => {

    return (
        <SoftBox mb={2}>
            <SoftTypography
                component="label"
                vatiant="caption"
                fontWeight="medium"
                fontSize={12}
            >
                {title}
            </SoftTypography>
            <SoftBox className="input-group">
                  <span className="input-group-text">
                      <i className="bi-calendar"></i>
                  </span>
                <input
                    type="date"
                    className="form-control"
                    id="departure-date-input"
                    aria-describedby="departure-date-label"
                    {...register(name, {
                        disabled: disabled,
                        validate: (value) => {
                            const today = new Date();
                            const date = new Date(value);
                            return date > today;
                        }
                    })}
                />
            </SoftBox>
        </SoftBox>
    )
}
export default DepartureDateInput;