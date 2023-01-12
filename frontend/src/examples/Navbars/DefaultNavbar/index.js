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

import {useState, useEffect} from "react";

// react-router components
import {Link} from "react-router-dom";

// prop-types is a library for typechecking of props.
import PropTypes from "prop-types";

// @mui material components
import Container from "@mui/material/Container";
import Icon from "@mui/material/Icon";

// Soft UI Dashboard React components
import SoftBox from "components/SoftBox";
import SoftTypography from "components/SoftTypography";
import SoftButton from "components/SoftButton";

// Soft UI Dashboard React examples
import DefaultNavbarLink from "examples/Navbars/DefaultNavbar/DefaultNavbarLink";
import DefaultNavbarMobile from "examples/Navbars/DefaultNavbar/DefaultNavbarMobile";

// Soft UI Dashboard React base styles
import breakpoints from "assets/theme/base/breakpoints";
import useLogout from "../../../hooks/useLogout";
import useAuth from "../../../hooks/useAuth";
import Notification from "../../../components/Notification";
import {IconButton} from "@mui/material";
import {navbarIconButton} from "../DashboardNavbar/styles";

function DefaultNavbar({transparent, light, action}) {
    const [mobileNavbar, setMobileNavbar] = useState(false);
    const [mobileView, setMobileView] = useState(false);
    const [openMenu, setOpenMenu] = useState(false);

    const signOut = useLogout();
    const {auth} = useAuth();

    const openMobileNavbar = ({currentTarget}) => setMobileNavbar(currentTarget.parentNode);
    const closeMobileNavbar = () => setMobileNavbar(false);

    const handleOpenMenu = (event) => setOpenMenu(event.currentTarget);
    const handleCloseMenu = () => setOpenMenu(false);


    useEffect(() => {
        // A function that sets the display state for the DefaultNavbarMobile.
        function displayMobileNavbar() {
            if (window.innerWidth < breakpoints.values.lg) {
                setMobileView(true);
                setMobileNavbar(false);
            } else {
                setMobileView(false);
                setMobileNavbar(false);
            }
        }


        /**
         The event listener that's calling the displayMobileNavbar function when
         resizing the window.
         */
        window.addEventListener("resize", displayMobileNavbar);

        // Call the displayMobileNavbar function to set the state with the initial value.
        displayMobileNavbar();

        // Remove event listener on cleanup
        return () => window.removeEventListener("resize", displayMobileNavbar);
    }, []);

    return (
        <Container>
            <SoftBox
                py={1.5}
                px={{xs: transparent ? 4 : 5, sm: transparent ? 2 : 5, lg: transparent ? 0 : 5}}
                my={2}
                mx={3}
                width="calc(100% - 48px)"
                borderRadius="section"
                shadow={transparent ? "none" : "md"}
                color={light ? "white" : "dark"}
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                position="absolute"
                left={0}
                zIndex={3}
                sx={({palette: {transparent: transparentColor, white}, functions: {rgba}}) => ({
                    backgroundColor: transparent ? transparentColor.main : rgba(white.main, 0.8),
                    backdropFilter: transparent ? "none" : `saturate(200%) blur(30px)`,
                })}
            >
                <SoftBox component={Link} to="/"  lineHeight={1}>
                    <SoftTypography variant="button" fontWeight="bold" fontSize={20} color={light ? "white" : "dark"} data-cy="app-name">
                        FlyAway
                    </SoftTypography>
                </SoftBox>
                <SoftBox color="inherit" display={{xs: "none", lg: "flex"}} m={0} p={0}>
                    {
                        auth.loggedIn ?
                            auth.roles.includes("ADMIN") ?
                                <>
                                    <DefaultNavbarLink icon="donut_large" name="dashboard" route="/users"
                                                       light={light}/>
                                    <DefaultNavbarLink icon="person" name="profile" route="/p" light={light}/>
                                    <DefaultNavbarLink icon="key" onClick={signOut} name="Sign out" route="/"
                                                       light={light}/>
                                    <IconButton
                                        size="small"
                                        color="inherit"
                                        sx={navbarIconButton}
                                        aria-controls="notification-menu"
                                        aria-haspopup="true"
                                        variant="contained"
                                        onClick={handleOpenMenu}
                                    >
                                        <Icon className={light ? "text-white" : "text-dark"}>notifications</Icon>
                                    </IconButton>
                                    <Notification openMenu={openMenu} handleCloseMenu={handleCloseMenu}/>
                                </> :
                                <>
                                    <DefaultNavbarLink icon="person" name="profile" route="/p" light={light}/>
                                    <DefaultNavbarLink icon="key" onClick={signOut} data-cy='sign-out-button' name="Sign out" route="/"
                                                       light={light}/>
                                    <IconButton
                                        size="small"
                                        color="inherit"
                                        sx={navbarIconButton}
                                        aria-controls="notification-menu"
                                        aria-haspopup="true"
                                        variant="contained"
                                        onClick={handleOpenMenu}
                                    >
                                        <Icon className={light ? "text-white" : "text-dark"}>notifications</Icon>
                                    </IconButton>
                                    <Notification openMenu={openMenu} handleCloseMenu={handleCloseMenu}/>
                                </> :
                            <>
                                <DefaultNavbarLink
                                    icon="account_circle"
                                    name="sign up"
                                    route="/sign-up"
                                    light={light}
                                />
                                <DefaultNavbarLink
                                    icon="key"
                                    name="sign in"
                                    route="/Login"
                                    light={light}
                                />
                            </>
                    }
                </SoftBox>
                {action &&
                    (action.type === "internal" ? (
                        <SoftBox display={{xs: "none", lg: "inline-block"}}>
                            <SoftButton
                                component={Link}
                                to={action.route}
                                variant="gradient"
                                color={action.color ? action.color : "info"}
                                size="small"
                                circular
                            >
                                {action.label}
                            </SoftButton>
                        </SoftBox>
                    ) : (
                        <SoftBox display={{xs: "none", lg: "inline-block"}}>
                            <SoftButton
                                component="a"
                                href={action.route}
                                target="_blank"
                                rel="noreferrer"
                                variant="gradient"
                                color={action.color ? action.color : "info"}
                                size="small"
                                circular
                            >
                                {action.label}
                            </SoftButton>
                        </SoftBox>
                    ))}
                <SoftBox
                    display={{xs: "inline-block", lg: "none"}}
                    lineHeight={0}
                    py={1.5}
                    pl={1.5}
                    color="inherit"
                    sx={{cursor: "pointer"}}
                    onClick={openMobileNavbar}
                >
                    <Icon fontSize="default">{mobileNavbar ? "close" : "menu"}</Icon>
                </SoftBox>
            </SoftBox>
            {mobileView && <DefaultNavbarMobile open={mobileNavbar} close={closeMobileNavbar}/>}
        </Container>
    );
}

// Setting default values for the props of DefaultNavbar
DefaultNavbar.defaultProps = {
    transparent: false,
    light: false,
    action: false,
    sticky: true,
};

// Typechecking props for the DefaultNavbar
DefaultNavbar.propTypes = {
    transparent: PropTypes.bool,
    light: PropTypes.bool,
    action: PropTypes.oneOfType([
        PropTypes.bool,
        PropTypes.shape({
            type: PropTypes.oneOf(["external", "internal"]).isRequired,
            route: PropTypes.string.isRequired,
            color: PropTypes.oneOf([
                "primary",
                "secondary",
                "info",
                "success",
                "warning",
                "error",
                "dark",
                "light",
            ]),
            label: PropTypes.string.isRequired,
        }),
    ]),
};

export default DefaultNavbar;
