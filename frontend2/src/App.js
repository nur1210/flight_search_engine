import './App.css';
import FlightsList from './pages/flightSearchPage.js';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import SearchForm from "./components/SearchForm";

function App() {
  return (
      <div className="App">
          <nav className="navbar navbar-expand navbar-dark bg-dark">
              <a href="/flightSearchPage" className="navbar-brand">
                  FlyAway
              </a>
              <div className="navbar-nav mr-auto"></div>
          </nav>
        <Router>
          <Routes>
            <Route path="/" element={<SearchForm />}/>
          </Routes>
        </Router>
      </div>
  );
}

export default App;
