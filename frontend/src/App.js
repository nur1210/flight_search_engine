import './App.css';
import {Route, Routes} from "react-router-dom";
import SearchForm from "./components/SearchForm";
import Login from "./pages/Login";
import Users from "./components/Users";
import PersistLogin from "./components/PersistLogin";

function App() {
    return (
        <div className="App">
            <nav className="navbar navbar-expand navbar-dark bg-dark">
                <a href="/" className="navbar-brand">
                    FlyAway
                </a>
                <div className="navbar-nav mr-auto"></div>
            </nav>
            <Routes>
                <Route element={<PersistLogin/>}>
                    <Route path="/" element={<SearchForm/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/users" element={<Users/>}/>
                </Route>
            </Routes>
        </div>
    );
}

export default App;

