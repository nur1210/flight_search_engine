/**
 =========================================================
 * Soft UI Dashboard React - v4.0.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/soft-ui-dashboard-react
 * Copyright 2022 Creative Tim (https://www.creative-tim.com)

 Coded by www.creative-tim.com

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 */

// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
import Grid from "@mui/material/Grid";

// Soft UI Dashboard React components
import SoftBox from "components/SoftBox";
import SoftTypography from "components/SoftTypography";

// Soft UI Dashboard React examples
import DefaultNavbar from "examples/Navbars/DefaultNavbar";
import PageLayout from "examples/LayoutContainers/PageLayout";

// Authentication layout components
import Footer from "components/Footer";

function BasicLayout({title, description, image, children, light}) {


    return (
        <PageLayout>
            <DefaultNavbar
                transparent
                light={light}
            />
            <SoftBox
                width="calc(100% - 2rem)"
                //height="50vh"
                borderRadius="lg"
                mx={2}
                my={2}
                pt={6}
                pb={35}
                mb={2}
                sx={{
                    backgroundImage: ({functions: {linearGradient, rgba}, palette: {gradients}}) =>
                        image &&
                        `${linearGradient(
                            rgba(gradients.dark.main, 0.6),
                            rgba(gradients.dark.state, 0.6)
                        )}, url(${image})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    backgroundRepeat: "no-repeat",
                }}
            >
                <Grid container spacing={3} justifyContent="center" sx={{textAlign: "center"}}>
                    <Grid item xs={10} lg={4}>
                        <SoftBox mt={6} mb={1}>
                            <SoftTypography variant="h1" color={light ? "white" : "dark"} fontWeight="bold">
                                {title}
                            </SoftTypography>
                        </SoftBox>
                        <SoftBox mb={2}>
                            <SoftTypography variant="body2" color={light ? "white" : "dark"} fontWeight="regular">
                                {description}
                            </SoftTypography>
                        </SoftBox>
                    </Grid>
                </Grid>
            </SoftBox>
            <SoftBox
                mt={{xs: -26, lg: -36}}
                px={1}
                width="calc(100% - 2rem)"
                mx="auto">
                <Grid container justifyContent="center" alignItems="center" alignContent="center">
                    <Grid item xs={17} sm={15} md={10} lg={9} xl={9}>
                        {children}
                    </Grid>
                </Grid>
            </SoftBox>
            <Footer/>
        </PageLayout>
    );
}

// Setting default values for the props of BasicLayout
BasicLayout.defaultProps = {
    title: "",
    description: "",
    light: true,
};

// Typechecking props for the BasicLayout
BasicLayout.propTypes = {
    light: PropTypes.bool,
    title: PropTypes.string,
    description: PropTypes.string,
    children: PropTypes.node.isRequired,
};

export default BasicLayout;
