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

// prop-types is a library for typechecking of props.
import PropTypes from "prop-types";

// @mui material components
import Menu from "@mui/material/Menu";
// import Grid from "@mui/material/Grid";

// Soft UI Dashboard React components
import SoftBox from "components/SoftBox";

// Soft UI Dashboard React examples
import DefaultNavbarLink from "examples/Navbars/DefaultNavbar/DefaultNavbarLink";
import {IconButton} from "@mui/material";
import {navbarIconButton} from "../DashboardNavbar/styles";
import Icon from "@mui/material/Icon";
import Notification from "../../../components/Notification";

function DefaultNavbarMobile({ open, close, light, auth, signOut }) {
  const { width } = open && open.getBoundingClientRect();

  return (
    <Menu
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "center",
      }}
      transformOrigin={{
        vertical: "top",
        horizontal: "center",
      }}
      anchorEl={open}
      open={Boolean(open)}
      onClose={close}
      MenuListProps={{ style: { width: `calc(${width}px - 4rem)` } }}
    >
      <SoftBox px={0.5}>
          {
              auth.loggedIn ?
                  auth.roles.includes("ADMIN") ?
                      <>
                          <DefaultNavbarLink icon="donut_large" name="dashboard" route="/users"
                                             light={light}/>
                          <DefaultNavbarLink icon="person" name="profile" route="/p" light={light}/>
                          <DefaultNavbarLink icon="key" onClick={signOut} name="Sign out" route="/"
                                             light={light}/>
                              <Icon className={light ? "text-white" : "text-dark"}>notifications</Icon>
                      </> :
                      <>
                          <DefaultNavbarLink icon="person" name="profile" route="/p" light={light}/>
                          <DefaultNavbarLink icon="key" onClick={signOut} data-cy='sign-out-button' name="Sign out" route="/"
                                             light={light}/>
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
    </Menu>
  );
}

// Typechecking props for the DefaultNavbarMenu
DefaultNavbarMobile.propTypes = {
  open: PropTypes.oneOfType([PropTypes.bool, PropTypes.object]).isRequired,
  close: PropTypes.oneOfType([PropTypes.func, PropTypes.bool, PropTypes.object]).isRequired,
};

export default DefaultNavbarMobile;
