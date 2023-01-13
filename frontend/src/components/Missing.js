import SoftBox from "./SoftBox";
import BasicLayout from "./BasicLayout";
import {Link} from "react-router-dom";

const Missing = () => {

    return (
        <BasicLayout
            light={false}
            title={"Oops!"}
            description={"Page not found"}
        >
            <SoftBox>
                <Link to="/">Visit Our Homepage</Link>
            </SoftBox>
        </BasicLayout>
    )
}

export default Missing;