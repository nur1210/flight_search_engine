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

const ROLES = {
    'User': "USER",
    'Admin': "ADMIN"
}

function App() {
    return (
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/" element={<SearchForm/>}/>
                    <Route path="/search-results" element={<SearchResults/>}/>
{/*
                    <Route path="register" element={<Register />} />
*/}
                    <Route path="unauthorized" element={<Unauthorized />} />

                    <Route element={<PersistLogin/>}>
                        <Route element={<RequireAuth allowedRoles={ROLES.Admin}/>}>
                            <Route path="/users" element={<Users/>}/>
                        </Route>
                    </Route>

                    <Route path="*" element={<Missing />} />
                </Route>
            </Routes>
    );
}

export default App;

