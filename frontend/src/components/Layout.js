import {Outlet} from "react-router-dom";
import {CssBaseline, ThemeProvider} from "@mui/material";
import theme from "assets/theme";


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