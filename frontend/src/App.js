import './App.css';
import {Route, Routes} from "react-router-dom";
import SearchForm from "./components/SearchForm";
import Login from "./pages/Login";
import Users from "./components/Users";
import PersistLogin from "./components/PersistLogin";
import SearchResults from "./components/SearchResults";
import Layout from "./components/Layout";
import RequireAuth from "./components/RequireAuth";
import Unauthorized from "./components/Unauthorized";
import Missing from "./components/Missing";
import SignUp from "./layouts/authentication/sign-up";
import Profile from "./components/Profile";
import {lazy} from "react";
import Verify from "./components/Verify";

const ROLES = {
    'User': "USER",
    'Admin': "ADMIN"
}

/*const Users = lazy (() => import ('./components/Users'));
const Login = lazy (() => import ('./pages/Login'));
const SearchResults = lazy (() => import ('./components/SearchResults'));
const SignUp = lazy (() => import ('./layouts/authentication/sign-up'));
const Unauthorized = lazy (() => import ('./components/Unauthorized'));
const Profile = lazy (() => import ('./components/Profile'));
const Missing = lazy (() => import ('./components/Missing'));*/


function App() {
    return (
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/" element={<SearchForm/>}/>
                    <Route path="/search-results" element={<SearchResults/>}/>
                    <Route path="/sign-up" element={<SignUp/>} />
                    <Route path="/verify" element={<Verify/>} />
                    <Route path="unauthorized" element={<Unauthorized />} />

                    <Route element={<PersistLogin/>}>
                        <Route element={<RequireAuth allowedRoles={ROLES.Admin}/>}>
                            <Route path="/users" element={<Users/>}/>
                        </Route>
                        <Route element={<RequireAuth allowedRoles={ROLES.User}/>}>
                            <Route path="/profile" element={<Profile/>}/>
                        </Route>
                    </Route>

                    <Route path="/" element={<Missing />} />
                </Route>
            </Routes>
    );
}

export default App;

