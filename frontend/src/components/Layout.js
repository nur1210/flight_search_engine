import {Outlet} from "react-router-dom";
import Navbar from "./Navbar";
import {CssBaseline, ThemeProvider} from "@mui/material";
import theme from "assets/theme";
import DefaultNavbar from "../examples/Navbars/DefaultNavbar";
import BasicLayout from "../layouts/authentication/components/BasicLayout";
import DashboardLayout from "../examples/LayoutContainers/DashboardLayout";
import curved6 from "../assets/images/curved-images/curved14.jpg";

function Layout() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <main className="App">
                <Outlet/>
            </main>
        </ThemeProvider>
    );
}

export default Layout;