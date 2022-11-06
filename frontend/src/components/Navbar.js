import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Offcanvas from 'react-bootstrap/Offcanvas';
import useLogout from "../hooks/useLogout";
import useAuth from "../hooks/useAuth";
import {Link, useLocation, useNavigate} from "react-router-dom";

function DarkNavbar() {
    const signOut = useLogout();
    const {auth} = useAuth();

    return (
        <>
            {[false].map((expand) => (
                <Navbar key={expand} bg="light" expand={expand} className="mb-3">
                    <Container fluid>
                        <Navbar.Brand>
                            <Link to="/" className="text-decoration-none text-dark">
                                <h3 className="display-12">FlyAway</h3></Link>
                        </Navbar.Brand>
                        <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-${expand}`}/>
                        <Navbar.Offcanvas
                            id={`offcanvasNavbar-expand-${expand}`}
                            aria-labelledby={`offcanvasNavbarLabel-expand-${expand}`}
                            placement="end"
                        >
                            <Offcanvas.Header closeButton>
                                <Offcanvas.Title id={`offcanvasNavbarLabel-expand-${expand}`}>
                                    Menu
                                </Offcanvas.Title>
                            </Offcanvas.Header>
                            <Offcanvas.Body>
                                <Nav className="justify-content-end flex-grow-1 pe-3">
                                    {
                                        auth.loggedIn ?
                                            <Nav.Link onClick={signOut}>Logout</Nav.Link>
                                            :
                                            <>
                                                <Link
                                                    to="/login"
                                                    className="text-decoration-none text-dark"
                                                    style={{paddingTop: "10px"}}>
                                                    Login
                                                </Link>
                                                <Link
                                                    to="/register"
                                                    className="text-decoration-none text-dark"
                                                    style={{paddingTop: "10px"}}>
                                                    Register
                                                </Link>
                                            </>
                                    }
                                    <Link
                                        to="/users"
                                        className="text-decoration-none text-dark"
                                        style={{paddingTop: "10px"}}>
                                        Users
                                    </Link>
                                </Nav>
                            </Offcanvas.Body>
                        </Navbar.Offcanvas>
                    </Container>
                </Navbar>
            ))}
        </>
    );
}

export default DarkNavbar;