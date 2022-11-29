import SoftBox from "./SoftBox";
import {Card, Grid, Menu, MenuItem, MenuList, Paper} from "@mui/material";
import SoftButton from "./SoftButton";
import SoftInput from "./SoftInput";
import SoftTypography from "./SoftTypography";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import AdultsInput from "./AdultsInput";


const Missing = () => {
    return (/*
        <article style={{ padding: "100px" }}>
            <h1>Oops!</h1>
            <p>Page Not Found</p>
            <SoftBox
             className="flexGrow">
                <Link to="/">Visit Our Homepage</Link>
            </SoftBox
            >
        </article>*/

        <BasicLayout
            title={"Let the journey begin"}
        >
            <Grid
                container
                direction="column"
                alignContent={"center"}
                sx={{minWidth: 650}}
            >
                <Card className={"my-2 card"}>
                    <SoftBox className={"card-body"}>
                        <Grid
                            display={"flex"}
                            justifyContent={"start"}
                        >
                            <SoftBox mt={2} ml={1}>
                                <Grid
                                    display={"flex"}
                                    flexDirection={"row"}
                                >
                                    <SoftBox mr={2} mb={1}>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            <input type={"radio"} value={"round"}/>
                                            Round trip
                                        </SoftTypography>
                                    </SoftBox>
                                    <SoftBox>
                                        <SoftTypography fontWeight={"bold"} fontSize={10}
                                                        textTransform={"uppercase"}
                                                        className={"text-start"}>
                                            <input type={"radio"} value={"oneway"}/>
                                            Oneway
                                        </SoftTypography>
                                    </SoftBox>
                                </Grid>
                            </SoftBox>
                        </Grid>
                        <SoftBox mt={1} mr={1} ml={1}>
                            <Grid
                                display={"flex"}
                                justifyContent={"center"}
                            >
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        From
                                    </SoftTypography>
                                    <SoftInput/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        To
                                    </SoftTypography>
                                    <SoftInput/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>Depart
                                    </SoftTypography>
                                    <SoftInput type={"date"}/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10}
                                                    textTransform={"uppercase"}
                                                    className={"text-start"}>Return
                                    </SoftTypography>
                                    <SoftInput type={"date"}/>
                                </SoftBox>
                                <SoftBox>
                                    <SoftTypography fontWeight={"bold"} fontSize={10} textTransform={"uppercase"}
                                                    className={"text-start"}>
                                        Cabin class & travelers
                                    </SoftTypography>
                                    <Paper>
                                        <MenuList>
                                            <MenuItem>Profile</MenuItem>
                                            <MenuItem>My account</MenuItem>
                                            <MenuItem>Logout</MenuItem>
                                        </MenuList>
                                    </Paper>
                                </SoftBox>
                            </Grid>
                        </SoftBox>
                        <SoftBox mb={1} mr={1}>
                            <Grid
                                display={"flex"}
                                justifyContent={"end"}
                            >
                                <SoftBox mt={3}>
                                    <SoftButton color={"dark"} size={"large"}>Search</SoftButton>
                                </SoftBox>
                            </Grid>
                        </SoftBox>
                    </SoftBox>
                </Card>
            </Grid>
        </BasicLayout>
    )
}

export default Missing;