import './App.css';
import {Route, Routes} from "react-router-dom";
import SearchForm from "./components/SearchForm";
import Login from "./pages/Login";
import Users from "./components/Users";
import PersistLogin from "./components/PersistLogin";
import Navbar from "./components/Navbar";
import SearchResults from "./components/SearchResults";

function App() {
    return (
        <div className="App">
            <Navbar/>
            <Routes>
                <Route element={<PersistLogin/>}>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/users" element={<Users/>}/>
                    <Route path="/" element={<SearchForm/>}/>
                    <Route path="/search-results" element={<SearchResults/>}/>
                </Route>
            </Routes>
        </div>
    );
}

export default App;

