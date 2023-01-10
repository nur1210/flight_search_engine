import team2 from "../assets/images/team-2.jpg";
import SoftBadge from "./SoftBadge";
import SoftTypography from "./SoftTypography";
import SoftBox from "./SoftBox";
import SoftAvatar from "./SoftAvatar";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import SoftButton from "./SoftButton";
import TextInputModal from "./TextInputModal";

function User({ image, firstName, lastName, email }) {
    return (
        <SoftBox display="flex" alignItems="center" px={1} py={0.5}>
            <SoftBox mr={2}>
                <SoftAvatar src={image} alt={`${firstName} ${lastName}`} size="sm" variant="rounded" />
            </SoftBox>
            <SoftBox display="flex" flexDirection="column">
                <SoftTypography variant="button" fontWeight="medium">
                    {`${firstName} ${lastName}`}
                </SoftTypography>
                <SoftTypography variant="caption" color="secondary">
                    {email}
                </SoftTypography>
            </SoftBox>
        </SoftBox>
    );
}


const userTableData = {
    columns: [
        { name: "user", align: "left" },
        { name: "status", align: "center" },
        { name: "message", align: "center" },
        { name: "action", align: "center" },
    ],
    rows: [],

    createUserTableData(users, handleDelete) {
        this.rows = users.map((user) => ({
            user: (
                <User
                    image={team2}
                    firstName={user.firstName}
                    lastName={user.lastName}
                    email={user.email}
                />
            ),
            status: (
                <SoftBadge
                    variant="gradient"
                    badgeContent={user.online ? "online" : "offline"}
                    color={user.online ? "success" : "secondary"}
                    size="xs"
                    container
                />
            ),
            message: (
                <SoftButton disabled={!user.online} iconOnly>
                        <TextInputModal email={user.email}/>
                </SoftButton>
            ),
            action: (
                <SoftButton onClick={() => handleDelete(user?.id)} iconOnly>
                    <DeleteForeverIcon/>
                </SoftButton>
            ),
        }));
    },
};

export default userTableData;